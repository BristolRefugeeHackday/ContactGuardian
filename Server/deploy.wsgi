#!/usr/bin/python

# Activate the virtual environment
activate_this = '/var/www/html/ContactGuardian/Server/venv/bin/activate_this.py'
with open(activate_this) as file_:
    exec(file_.read(), dict(__file__=activate_this))

# Configure system path and logging to apache error.log (normally found in /var/log/apache2/error.log)
import sys
import logging
logging.basicConfig(stream=sys.stderr)

sys.path.insert(0,"/var/www/html/ContactGuardian/Server")

# Import the Flask app and run
from app import app as application

application.secret_key = 'Add your secret key'
