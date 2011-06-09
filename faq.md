# Frequently Asked Questions

## What is JGGApi?
The JGGApi is a Java Gadu-Gadu protocol implementation. It is meant to be used for GUI application designers. JGGApi uses that well-known design patterns that minimize learning curve. As a result developers can write Gadu-Gadu clients faster than before.

## What is the JGGApi license?
We use GPL open source license, therefore if you want to write the Gadu-Gadu client it must have GPL license as well. To find out more about open-source licenses point your browser at: www.opensource.org/licenses/

## Who develops JGGApi?
The project was founded by Marcin Naglik (mnaglik@gazeta.pl) in 2003.
In November 2004 Mateusz Szczap (mati@sz.home.pl) joined the project and helped with the development and documentation process.

## What version of JVM (java virtual machine) can I use?
The library requires JDK 1.4+.

## Who should use this library?
This library can be used by anyone who wishes to write any kind of application or module that will interact with Gadu-Gadu server and it's users.

Typical uses:

* GUI application, ie. SWT/Swing.
* Proxy to bot such as A.L.I.C.E. (http://www.alicebot.org
* Http servlet gateway.
* Command line client, similar to ekg, using JCurses library.
* Flash client and gateway to java library.
* Gadu-Gadu WebService gateway.
It is also possible that this library could become part of CRM or workflow system implemented as a module that would allow interaction between some corporate system and IM users.

## Is it legal to use this library?
This is quite unclear. Even though the company who developed Gadu-Gadu protocol states on their web-site that only applications authorized by that company can legally connect to servers supplied by SMS Express Sp. z.o.o company there is many open-source applications written mostly in C that use company's servers. Some applications such as: EKG, Kadu, Gaim are available in official Linux distributions.

## Is JGGApi better then libgadu from EKG project?
Yes and no. Libgadu from EKG project is more tested since it has a huge open-source community feedback. The disadvantage of the libgadu is that it is written in c language and there are some porting issues. JGGApi is a library written in Java and will run on every computer that has JRE (Java Runtime Environment) installed. In addition, the JGGApi is an object-oriented library and has a clear design. It should be relatively easy for a new developer to write a Gadu-Gadu application using this library.

## I want to write a Gadu-Gadu midlet for my cell phone, can I use your library?
No. Java enabled phones have J2ME VM. It is limited and JGGApi was developed using JDK 1.4.2 VM which is J2SE compilant library.

## How can I contribute to this project?
If you are a Java developer who wants to write a Gadu-Gadu application you can help by submiting bug reports and patches fixing found issues.
Also, there is a TODO list that always has some issues on it. Please feel free to look at it if you want to help us. If you convince us that you are valuable to the project and that you have Java skills we will be happy to include you on the list of our developers.
You do not have to be a Java Developer or a Computer Engineer to contribute to this project. We are looking for web site mainteners and testers. Bug reports are very welcome.

## Does JGGApi depend on any library?
Yes, it depends on commons-logging-1.0.4 framework and backport-util-concurrent from JDK 1.5.
If you want to find out more about commons-logging framework take a look at: http://jakarta.apache.org/commons/logging/

## Where should I begin?
First of all, you have to grab a fresh release of JGGApi from Download.
Second of all, please read our Getting Started.
Third of all, your can browse JGGApi's Java Docs and sources online.
Last but not least, start writing your application or module and get some hands on.

