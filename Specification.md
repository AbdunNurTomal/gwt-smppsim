## Tools ##

SMPPSim is a GPL-licensed application which simulates a SMPP SMSC, and offers a web user interface through which one can send Mobile Originated messages to one's SMPP-supporting application.

Google Web Toolkit is Google's rapid web user interface development environment for Java.

## Requirements ##

In this project, Gateway Technolabs will use GWT to create a better user interface for SMPPSim, and extend SMPPSim to also support Mobile Terminated message support.

When a user loads this interface in their web browser, they are assigned a fake mobile number, which they can then use to send and receive messages through SMPPSim.

When the SMPP-based application connected to SMPPSim through SMPP submits a Mobile Terminated message, it pops out in that user's user interface who has selected the fake mobile number which is found in the MT SMS message SMPPSim receives from the application.

When the user submits a Mobile Originated fake SMS message, that also shows up on that user's log and user interface, as shown in the user interface mockup on this page.

The user can change their fake handset number at any time, at which time the application will load that number's message log to its user interface. When two simultaneous users have selected the same number, they both are able to see the same MO/MT message logs, and are both able to send and receive messages.

User interface mockup:

```
________________________________________________________________________________
|                                                                              |
| Handset number:    #############################   [Change]                  |
| Service number:    #############################   [Send Message]            |
| Message:                                                                     |
|          #############################                                       |
|          #############################                                       |
|          #############################                                       |
|                                                                              |
|  Mobile Originated messages                      Mobile Terminated messages  |
|  2010-05-03 12:12.12 [040123123->16123]                                      |
|  This is a Mobile Originated message 5.                                      |
|                                      2010-05-03 12:10.00 [16123->040123123]  |
|                                      This is a Mobile Terminated message 4.  |
|  2010-05-03 12:05.00 [040123123->16123]                                      |
|  This is a Mobile Originated message 3.                                      |
|  2010-05-03 12:04.00 [040123123->16123]                                      |
|  This is a Mobile Originated message 2.                                      |
|                                      2010-05-03 12:02.00 [16123->040123123]  |
|                                      This is a Mobile Terminated message 1.  |
|                                                                              |
|______________________________________________________________________________|
```

The application is to be used by multiple simultaneous users (tens of users, not thousands) so that each user sees the messages to/from the handset number they have chosen. If two people simultaneously select the same handset number, they should see the same messages.

There is no requirement to save the sent messages, so the application can use the JDK built-in database Derby.

The application must be mindful of the GSM 03.38 standard character sets, and if the user tries to send characters which don't fit in ISO-LATIN character set, the application must create one or multiple chained UCS2-encoded messages. Please see SMPP 3.4 standard "5.2.19 data\_coding". The user interface must use the UTF-8 character set by default.