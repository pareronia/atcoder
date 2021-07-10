# statics
SRC_ROOT := src
SRC_ROOT_MAIN := $(SRC_ROOT)/main
JAVA_ROOT := $(SRC_ROOT_MAIN)/java
RESOURCES_ROOT_MAIN := $(SRC_ROOT_MAIN)/resources
TEMPLATE_DIR := $(RESOURCES_ROOT_MAIN)/template
ATCODER_ROOT := $(JAVA_ROOT)/com/github/pareronia/atcoder
CP := cp
SED := sed
MKDIR := mkdir
TOUCH := touch
MV := mv

# vars
TYPE_UPC = $(shell echo $(TYPE) | tr a-z A-Z)
TYPE_LOC = $(shell echo $(TYPE) | tr A-Z a-z)
SOLUTION_DIR = $(ATCODER_ROOT)/$(TYPE_LOC)/_$(ROUND)/$(PROBLEM)
LAUNCH_DIR = $(ECLIPSE_WORKSPACE)/.metadata/.plugins/org.eclipse.debug.core/.launches

#: Default target - create_main
.DEFAULT_GOAL := create_main

#: Create new Main from template (params TYPE, ROUND, PROBLEM)
create_main:
	$(MKDIR) --parents $(SOLUTION_DIR)
	$(CP) --no-clobber $(TEMPLATE_DIR)/Main.java $(SOLUTION_DIR)
	$(CP) --no-clobber $(TEMPLATE_DIR)/Main.launch $(LAUNCH_DIR)
	$(SED) \
		--in-place 's/%TYPE%/$(TYPE_LOC)/g;s/%ROUND%/$(ROUND)/g;s/%PROBLEM%/$(PROBLEM)/g' \
		$(SOLUTION_DIR)/Main.java
	$(SED) \
		--in-place 's/%TYPE%/$(TYPE)/g;s/%ROUND%/$(ROUND)/g;s/%PROBLEM%/$(PROBLEM)/g' \
		$(LAUNCH_DIR)/Main.launch
	$(MV) $(LAUNCH_DIR)/Main.launch "$(LAUNCH_DIR)/AtCoder $(TYPE_UPC) $(ROUND) $(PROBLEM).launch"
	$(TOUCH) $(SOLUTION_DIR)/sample.in
	$(TOUCH) $(SOLUTION_DIR)/sample.out

