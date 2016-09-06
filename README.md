# Contact Guardian

When complete, Contact Guardian is an application that can be used by asylum seekers to notify a set of prechosen contacts that you have been detained.

This application was created by Elliot and Dale at the [Bristol Refugee Hackday](https://bristolrefugeehackday.wordpress.com) (June 2016).

## Background - why is this application needed?
Content to be written-up. Check back soon.


## Technology overview

### Selecting contacts
An Android app (in the `Android` directory) allows you to select a set of designated contacts that you wish to alert in the event that you are unexpectedly detained.

### Notifying contacts
A voice menu system is made using the [Twillo Calling API](https://www.twilio.com/docs/api/rest/call). A python flask application is used for the logic to determine the appropriate [TwiML markup](https://www.twilio.com/docs/api/twiml) that should be outputted.  (This code is stored in the `Server` directory.)


## Installation

```
# Clone the repository and move inside it
git clone https://github.com/BristolRefugeeHackday/ContactGuardian.git
cd ContactGuardian

# Go to the notifcation directory and set-up a virtual environment
cd Server
virtualenv venv

# Activate the environment
source venv/bin/activate

# Download dependencites
pip install -r requirements.txt




## Local development

[Ngrok](https://ngrok.com/) can be useful for setting up a test environment with a public URL that an be accessed from Twillo.

```
# Run the webserver using ngrok
python app.py

# Expose port 5000 to the web
ngrok http 5000
```

You will need to sign-up with an Twillo account. Once set-up:

```
# 1. Create a TwiML app
Twillo console -> Programmable Voice -> Tools -> Twillo apps -> Create new app
# Enter the ngrok URL as the voice request URL.

# 2. Link your TwiML app to a number
Twillo console -> Numbers -> Phone numbers -> Add/edit phone number

Voice 'Configure with': TwiML app
TwiML app: [YOUR APP NAME FROM STEP 1]

Calls to your Twillo number should now be routed to the application.
