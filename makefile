#
# makefile 
#
# input file for 'make' build tool ( /usr/bin/make )
# to build solution
#
# @author Dr. Fenwick
# @author Matt Stone
# @version Spring 2017
#

JUNIT_JAR = /usr/share/java/junit-4.10.jar
HAMCREST_JAR = /usr/share/java/hamcrest/core-1.1.jar

default: 
	@echo "usage: make target"
	@echo "available targets: compile, test, clean"

compile: ./javatrix/Matrix.java ./javatrix/tests/MatrixBasicTests.java ./javatrix/tests/TestConstructor.java
	javac -cp .:$(JUNIT_JAR) ./javatrix/tests/MatrixBasicTests.java
	javac -cp .:$(JUNIT_JAR) ./javatrix/tests/TestConstructor.java
	
	javac ./javatrix/Matrix.java

clean:
	rm -f ./javatrix/Matrix.class
	rm -f ./javatrix/tests/*.class

# java packages are part of class name; when specifying the directory (going through package folders) use dots like below
# ie javatrix/tests/MatrixBasicTests will not work; it must be javatrix.tests.MatrixBasicTests
# see this link: http://stackoverflow.com/questions/15548580/junit-cannot-find-class
test: ./javatrix/Matrix.class ./javatrix/tests/MatrixBasicTests.class 
	java -cp .:$(JUNIT_JAR):$(HAMCREST_JAR) org.junit.runner.JUnitCore javatrix.tests.MatrixBasicTests
	java -cp .:$(JUNIT_JAR):$(HAMCREST_JAR) org.junit.runner.JUnitCore javatrix.tests.TestConstructor


