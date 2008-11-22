CLASSPATH = src

JFLAGS = -classpath $(CLASSPATH)
JCFLAGS = -d bin -Xlint
JAVAC = javac $(JFLAGS) $(JCFLAGS)
JAVA = java $(JFLAGS)

JAVASRC = $(shell ls src/uc/*.java) $(shell ls src/grammar/*.java)  $(shell ls src/parser/*.java) $(shell ls src/scanner/*.java) $(shell ls src/semantic/*.java) $(shell ls src/ll1grammar/*.java)
CLASSES = $(JAVASRC:.java=.class)

.SUFFIXES : .java .class

.java.class :
	$(JAVAC) $<

all : compile

mkbindir:
	mkdir -p bin

compile : mkbindir $(CLASSES)
		@echo Micro Compiler (Non-Universal) has been compiled.

clean :
	rm -f bin/uc/*.class
	rm -f bin/grammar/*.class
	rm -f bin/parser/*.class
	rm -f bin/scanner/*.class
	rm -f bin/semantic/*.class
	rm -f bin/ll1grammar/*.class
