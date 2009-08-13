# Naive compiler for brainfuck
# Usage: jruby -rubygems simple-compiler.rb <path/to/brainfuck/program.bf>
# Output: Program.class
# Run the output with: java -cp . Program

# Author: Martin McNulty (martin@martinmcnulty.co.uk, twitter.com/shamblepop)

# Generates invalid class files if the brainfuck program is too long, as it sticks all
# the generated code in one method (void run()).

require 'java'
require 'jruby'
require 'bitescript'
require 'pathname'

import java.io.InputStream
import java.io.OutputStream
import java.io.PrintStream
import java.lang.System

$labelstack = []

def eachcharinfile(filename)
  File.open(filename, 'r') do |line|
    while char = line.getc do
      yield char
    end
  end
end

def compile(filename)
  classname = Pathname.new(filename).basename.to_s.split(/\./)[0].capitalize

  builder = BiteScript::FileBuilder.build(filename) do
    
    public_class classname, object do

      private_field "data", byte[]
      private_field "pointer", int

      main do
        new this
        dup
        invokespecial this, "<init>", [void]
        invokevirtual(this, "run", [void])
        returnvoid
      end

      public_constructor do
        aload 0
        invokespecial object, "<init>", [void]
        ldc 32768
        newarray byte
        aload 0
        swap
        putfield this, "data", byte[]
        returnvoid
      end

      public_method "run", void do
        eachcharinfile(filename) do |c|
          case c.chr
          when '+'
            withdata this do 
              iconst_1
              iadd
            end
          when '-'
            withdata this do
              iconst_1
              isub
            end
          when '>'
            withpointer this do
              iconst_1
              iadd
            end
          when '<'
            withpointer this do
              iconst_1
              isub
            end
          when ','
            input this
          when '.'
            output this
          when '['
            openloop this
          when ']'
            closeloop this
          end
        end
        returnvoid
      end

      public_method "print", void do
        getstatic System, :out, PrintStream
        aload 0
        getfield this, "data", byte[]
        iconst_0
        baload
        invokevirtual PrintStream, :println, [void, int]
        returnvoid
      end
    end
  end

  builder.generate do |name, builder|
    bytes = builder.generate
    outfile = File.new(classname + '.class', 'wb')
    outfile.puts(bytes)
    outfile.close
  end
end

def loaddata(this)
  aload 0
  getfield this, "data", byte[]
  aload 0
  getfield this, "pointer", int
  baload
end

def withdata(this)
  loaddata(this)
  yield
  aload 0
  getfield this, "data", byte[]
  swap
  aload 0
  getfield this, "pointer", int
  swap
  bastore
end

def withpointer(this)
  aload 0
  getfield this, "pointer", int
  yield
  aload 0
  swap
  putfield this, "pointer", int
end

def input(this)
  withdata this do 
    getstatic System, :in, InputStream
    invokevirtual InputStream, :read, [int]
    
    # Store original value, if we've reached the end of the file
    dup
    iconst_1
    iadd

    dontstore = label
    endlabel = label

    ifeq dontstore
    swap
    pop
    goto endlabel
    dontstore.set!
    pop
    endlabel.set!
  end
end  

def output(this)
  withdata this do
    dup
    getstatic System, :out, PrintStream
    swap
    invokevirtual OutputStream, :write, [void, int]
    getstatic System, :out, PrintStream
    invokevirtual OutputStream, :flush, [void]
  end
end

def openloop(this)
  open = label
  close = label
  open.set!
  $labelstack.push [open, close]
  loaddata this
  ifeq close
end

def closeloop(this)
  labelpair = $labelstack.pop
  open = labelpair[0]
  close = labelpair[1]
  close.set!
  loaddata this
  ifne open
end

compile(ARGV[0])
