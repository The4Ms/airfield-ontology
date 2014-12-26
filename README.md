airfield-ontology
========================

A simple airfield system that stores several airfields information using in RDF format and provide a simple web interface to query this information.

# Getting Started
First download and install [git] and [maven], ([how to install maven])

* On windows, navigate to an empty folder, right click and click "Git Bash Here"
* On Linux and Mac, open a terminal and cd to an empty folder

then write the following commands

    git clone https://github.com/The4Ms/airfield-ontology

Check based on your preferred IDE:
* [How do I import a Maven project into Eclipse?]
* [Best Practices for Apache Maven in NetBeans - Open existing project]
* Or search for maven integeration for your IDE

# Eclipse
if you use eclipse, Preferences -> Maven -> Check on "Download artifact Source" and "Download artifact JavaDocs"

# Running the server
open the bash in the project folder, write 

    mvn tomcat7:run

    
[maven]:http://maven.apache.org/download.cgi

[git]:http://git-scm.com/downloads

[how to install maven]:http://www.mkyong.com/maven/how-to-install-maven-in-windows/

[How do I import a Maven project into Eclipse?]:http://www.avajava.com/tutorials/lessons/how-do-i-import-a-maven-project-into-eclipse.html

[Best Practices for Apache Maven in NetBeans - Open existing project]:http://wiki.netbeans.org/MavenBestPractices#Open_existing_project