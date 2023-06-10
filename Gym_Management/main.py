from flask import Flask

from public import public
from admin import admin
from api import api
from employee import employee
from physician import physician

import smtplib
from email.mime.text import MIMEText
from flask_mail import Mail


app=Flask(__name__)
app.secret_key="main"
app.register_blueprint(public)
app.register_blueprint(admin,url_prefix='/admin')
app.register_blueprint(employee,url_prefix='/employee')
app.register_blueprint(physician,url_prefix='/physician')
app.register_blueprint(api,url_prefix='/api')


mail=Mail(app)
app.config['MAIL_SERVER']='smtp.gmail.com'
app.config['MAIL_PORT'] = 587
app.config['MAIL_USERNAME'] = 'handoutfoodcommunity@gmail.com'
app.config['MAIL_PASSWORD'] = 'xkyokbuzwopremsz'
app.config['MAIL_USE_TLS'] = False
app.config['MAIL_USE_SSL'] = True


app.run(debug=True,port='5016',host="0.0.0.0") 