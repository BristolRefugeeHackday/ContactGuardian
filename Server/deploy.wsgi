#!/usr/bin/python

# Activate the virtual environment
activate_this = '/var/www/ContactGuardian/Server/venv/bin/activate_this.py'
with open(activate_this) as file_:
    exec(file_.read(), dict(__file__=activate_this))

# Import the app and run
import sys
import logging
logging.basicConfig(stream=sys.stderr)

sys.path.insert(0,"/var/www/ContactGuardian/Server")

from app import app as application

application.secret_key = 'Add your secret key'
