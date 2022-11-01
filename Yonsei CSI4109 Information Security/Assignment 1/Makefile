# Signifies our desired python version
PYTHON = python3

# .PHONY defines parts of the makefile that are not dependant on any specific file
# This is most often used to store functions
.PHONY = help setup test run clean

# Defining an array variable
FILES = input output

# Defines the default target that `make` will to try to make, or in the case of a phony target, execute the specified commands
# This target is executed whenever we just type `make`
.DEFAULT_GOAL = run

# The @ makes sure that the command itself isn't echoed in the terminal
help:
	@echo "---------------HELP-----------------"
	@echo "I do not think this is necessary."
	@echo "But, I've made a Makefile regardless."
	@echo "To run the code, use make run."
	@echo "------------------------------------"

# This generates the desired project file structure
	
	
run:
	cp set_key.py secure_house

# In this context, the *.project pattern means "anything that has the .project extension"
clean:
	rm secure_house
