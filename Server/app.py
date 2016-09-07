from flask import Flask, request, redirect, session, url_for
from werkzeug.routing import RequestRedirect
import twilio.twiml

app = Flask(__name__)
app.secret_key = 'default-secret-key'


def process_no_input_response(resp, endpoint, num_retries_allowed=3):
    """Handle cases where the caller does not respond to a `gather` command.
       Determines whether to output a 'please try again' message, or redirect 
       to the hand up process
    
    Inputs:
      resp -- A Twillo resp object
      endpoint -- the Flask endpoint
      num_retries_allowed -- Number of allowed tries before redirecting to the hang up process

    Returns:
      Twillo resp object, with appropriate ('please try again' or redirect) syntax
    """

    # Add initial caller message
    resp.say("Sorry, I did not hear a response.")
    
    session['num_retries_allowed'] = num_retries_allowed

    # Increment number of attempts
    if endpoint in session:
        session[endpoint] += 1
    else:
        session[endpoint] = 1

    if session[endpoint] >= num_retries_allowed:
        # Reached maximum number of retries, so redirect to a message before hanging up
        resp.redirect(url=url_for('bye'))
    else:
        # Allow user to try again
        resp.say("Please try again.")
        resp.redirect(url=url_for(endpoint))

    return resp


@app.route('/bye', methods=['GET', 'POST'])
def bye():
    """Hangup after a number of failed input attempts."""

    resp = twilio.twiml.Response()
    resp.say("You have reached the maximum number of retries allowed. Please hang up and try calling again.")
    resp.hangup()

    return str(resp)


@app.route('/', methods=['GET', 'POST'])
def step_one():
    """Entry point to respond to incoming requests."""

    resp = twilio.twiml.Response()
    with resp.gather(numDigits=6, action="/post_step_one_logic", method="POST") as gather:
    	gather.say("Hello. This is Contact Guardian. Please enter your pin.")

    return str(process_no_input_response(resp, request.endpoint))


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

    resp.say("Sorry, I did not hear a response. Please try again.")
    resp.redirect(url=url_for('step_two'))

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
