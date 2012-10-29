gettext-resource-bundle
=======================

This project includes a Java ResourceBundle implementation that can parse and 
read PO files as defined by the GNU gettext project 
<http://www.gnu.org/software/gettext/>.

The obvious question here is why would anyone even need this.  The preferred
approach when integrating gettext with Java is to use the msgfmt command
to create Java ResourceBundles directly.  

This is a fine approach, and in most production systems, this method should
be used.  The Use Case that this project is built around is that while 
developing a system, a programmer or translator may want to make changes to
PO files on the fly and see there changes automatically within the system.

This project provides a ResourceBundle that can read PO files (without any
dependency on the msgfmt binary), as well as a ResourceBundle.Contorl
implementation that will load PO files from either the classpath or file 
system.  When loading from the file system, the Control implementation
will also reload the files as needed when changes have occured on the file 
system.



