# Contact Guardian

When complete, Contact Guardian is an application that can be used by asylum seekers to notify a set of prechosen contacts that you have been detained.

This application was created by Elliot and Dale at the [Bristol Refugee Hackday](https://bristolrefugeehackday.wordpress.com) (June 2016).

## Background - why is this application needed?
Content to be written-up. Check back soon.


## Technology overview

### Selecting contacts
An Android app allows you to select a set of designated contacts that you wish to alert in the event that you are unexpectedly detained.

### Notifying contacts
A menu system is made using the [Twillo Calling API](https://www.twilio.com/docs/api/rest/call). PHP is used for the logic to determine the [TwiML markup](https://www.twilio.com/docs/api/twiml) that should be outputted.


## Installation

Clone this repository and move inside it
```
git clone https://github.com/BristolRefugeeHackday/ContactGuardian.git
cd ContactGuardian
```

Go to the notifcation directory and set-up a virtual environment
```
cd Server
virtualenv venv
```

Activate the environment
`source venv/bin/activate`

Download dependencites
`pip install -r requirements.txt`

Run the webserver


Sign-up with an Twillo account

Route incoming calls to `Server/entry.php`