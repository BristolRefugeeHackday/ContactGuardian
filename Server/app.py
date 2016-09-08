from flask import Flask, request, redirect, session, url_for
import twilio.twiml

app = Flask(__name__)
app.secret_key = 'default-secret-key'


@app.route('/timeout', methods=['GET', 'POST'])
def timeout():
    """Determines whether to output a 'please try again' message, or if they
       should be cut off after a number of (i.e. 3) failed input attempts.
       Should include 'source' as part of the GET payload.
    """

    # Get source of the timeout
    source = request.args.get('source')

    # Add initial caller message
    resp = twilio.twiml.Response()
    resp.say("Sorry, I did not hear a response.")

    # Increment number of attempts
    if source in session:
        session[source] += 1
    else:
        session[source] = 1

    # Logic to determine if user should be cut off, or given another chance
    if session[source] >= 3:
        # Reached maximum number of retries, so redirect to a message before hanging up
        resp.say("""
            You have reached the maximum number of retries allowed. Please hang up 
            and try calling again.
            """)
        resp.hangup()
    else:
        # Allow user to try again
        resp.say("Please try again.")
        resp.redirect(url=url_for(source))

    return str(resp)


@app.route('/', methods=['GET', 'POST'])
def step_one():
    """Entry point to respond to incoming requests."""

    resp = twilio.twiml.Response()
    with resp.gather(numDigits=6, action="/post_step_one_logic", method="POST") as gather:
    	gather.say("Hello. This is Contact Guardian. Please enter your pin.")
    resp.redirect(url=url_for('timeout', source=request.endpoint))

    return str(resp)


@app.route('/post_step_one_logic', methods=['GET', 'POST'])
def post_step_one_logic():
    """Handle response from step 1."""
    #FIXME Link up to a database
    # Pin currently set to "123456". Lovely.

    digit_pressed = request.values.get('Digits', None)
    resp = twilio.twiml.Response()

    if digit_pressed == "123456":
    	resp.say("Hello {}. You have 3 contacts saved.".format("Dale"))
    	resp.redirect(url=url_for('step_two'))
    elif digit_pressed != "123456":
    	resp.say("Sorry, that pin is not recognaized. Please try again.")
    	resp.redirect(url=url_for('step_one'))
    
    return str(resp)


@app.route('/step_two', methods=['GET', 'POST'])
def step_two():
    """Give instructions for alerting a single or all contacts."""
    
    resp = twilio.twiml.Response()
    with resp.gather(numDigits=1, action="/post_step_two_logic", method="POST") as gather:
    	gather.say("To alert a single contact, press 1. To alert all your contacts press 2")
    resp.redirect(url=url_for('timeout', source=request.endpoint))

    return str(resp)


@app.route('/post_step_two_logic', methods=['GET', 'POST'])
def post_step_two_logic():
    """Handle response from step 2"""

    digit_pressed = request.values.get('Digits', None)
    resp = twilio.twiml.Response()

    if digit_pressed == "1":
    	resp.redirect(url=url_for('step_three'))
    elif digit_pressed == "2":
    	resp.redirect(url=url_for('step_four'))
	
	return str(resp)


if __name__ == '__main__':
	app.run(debug=True)
