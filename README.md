### Description

This is a GUI which hopefully helps users configure, run and evaluate recommendations faster using the LibRec library 2.0 found here https://github.com/guoguibing/librec

Some features of LibRec cannot be used in the GUI and can only use text datasets.

### Features
- Easy control through the GUI via mouse and keyboard which is more intuitive to the user and
offering more visual feedback.

- Tables with filter, sort, rearrange, readjust and scroll options to provide an organized and
efficient way of viewing and working with the data. Also has toggle checkbox to instantly select
all or deselect all rows visible in current view (ignores filtered out rows).

- Ability to automate running many recommendation jobs in a sequence so user doesn’t have to
manually start each recommendation after another.

- Access to different windows and tabs at any time.

- Confirmation dialogs provide slight defense against user misclicking and accidentally doing
something which could result in data loss or wasted time. Can also help against accidental and
intentional “spam-clicking” of buttons which execute some action.

- Freedom to adjust parameters for each dataset and algorithm for obtaining varying results.

- Dataset and algorithm parameters editable inside GUI to provide simpler way for user to edit
parameters. Supports specifying a set of values for each parameter, from which,
recommendations can be generated for all possible combinations of the parameter values.

- Default parameters are available on startup of the system.
  - Dataset parameter editor
  - Can specify column format of the dataset file (user-item-rating or user-itemrating-timestamp are supported formats.
  - Can specifying whether to binarise ratings (set a threshold value to transform
ratings into either 1 or 0) and set the values for the threshold.
  - Can choose how to split the data into training set and testing set such as by ratio,
certain number of user and/or items, and/or by k-fold cross-validation (with
choice of number of folds).

- Algorithm parameter editor
  - Works similarly to the dataset parameter editor by allowing ranges of values to
specifying by typing them into the appropriate table cells.
  - However, every parameter is necessary, so there is not checkbox column for
selecting the parameters.

- Notification(s) notifying user of finished processing of recommendations, eliminating need for
constant monitoring of program.
- History of recommendation outputs to allow user to access the recommendations later if
needed

- Display of algorithm performance in an organised format, accessible for later use with various
information-finding helper features such as search, sort, etc.

- Generation of bar charts for algorithm performance provides easy way for user to display the
algorithm performance visually. Generation is also easy and fast, requiring only ticking the
checkboxes next to the job output and then clicking the “GRAPH SELECTED” button. Each bar
also shows the exact value of the bar on mouseover. Graphs can also be zoomed in vertically
using click and drag of the mouse.

- Log output in the GUI to provide users a way to see progress of the recommendation jobs.
- Descriptive tooltips on mouseover of GUI components to provide users with descriptions and
tips to help them understand how each component of GUI works with the recommendation
system.

- Various usability features such as resizability of windows, and consistent placement of buttons,
tables and textboxes in each tab to prevent confusion.

- Capability to add any dataset for the system to use by placing the dataset file as a .txt file into
the appropriate folder and the system will automatically detect the file on start.

- Can be run on any system which has a Java virtual machine and which supports GUIs.

- Exception-handling so the program does not crash and easy recovery by restarting the program
if necessary.

### How to run
Can run by:
1. opening core folder as project in IDE OR
2. running the LibRecGUI.jar file in the core folder.

### Acknowledgement

All the info about original LibRec can be found at https://github.com/guoguibing/librec

### GPL License

LibRec is [free software](http://www.gnu.org/philosophy/free-sw.html): you can redistribute it and/or modify it under the terms of the [GNU General Public License (GPL)](http://www.gnu.org/licenses/gpl.html) as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version. LibRec is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details. 

You should have received a copy of the GNU General Public License along with LibRec. If not, see http://www.gnu.org/licenses/.
