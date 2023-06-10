from flask import Blueprint,render_template,redirect,url_for,request,session
from database import*

public=Blueprint('/',__name__)

@public.route('/',methods=['get','post'])
def index():
	session.clear()
	return render_template('index.html')


@public.route('/login',methods=['get','post'])
def public_home():
	session.clear()
	if 'login' in request.form:
		username=request.form['uname']
		password=request.form['pass']
   
		q="select * from login where username='%s' and password='%s'"%(username,password)
		res=select(q)
		if res:
			session['login_id']=res[0]['login_id']
			if res[0]['user_type']=="admin":
				return redirect(url_for('admin.admin_home'))
			if res[0]['user_type']=="employee":
				return redirect(url_for('employee.employee_home'))
			if res[0]['user_type']=="physician":
				return redirect(url_for('physician.physician_home'))

	return render_template('login.html')

@public.route('/gallery',methods=['get','post'])
def gallery():

	return render_template('gallery.html')
