from flask import Flask,Blueprint,redirect,url_for,render_template,request,session
from database import*
import uuid

physician=Blueprint('physician',__name__)

@physician.route('physician_home',methods=['get','post'])
def physician_home():
	if not session.get("login_id") is None:

		return render_template('physician_home.html')
	else:
		return redirect(url_for('/.public_home'))

@physician.route('physician_view_users',methods=['get','post'])
def physician_view_users():
	if not session.get("login_id") is None:
		data={}
		q="SELECT * FROM `users` INNER JOIN `payments` USING(`user_id`) WHERE `payment_for`='Subscription' group by users.user_id"
		# q="SELECT * FROM `users` INNER JOIN `subscription` USING(`user_id`)"
		res=select(q)
		data['view_user']=res

		if 'action' in request.args:
			action=request.args['action']
			w=request.args['w']
			h=request.args['h']
			uid=request.args['uid']
		else:
			action=None
		
		if action=="bmi":
			q="select * from users where user_id='%s'"%(uid)
			res=select(q)
			data['bmi_ratio']=res

		return render_template('physician_view_users.html',data=data)
	else:
		return redirect(url_for('/.public_home'))

@physician.route('physician_create_personalised_diet_plan',methods=['get','post'])
def physician_create_personalised_diet_plan():
	if not session.get("login_id") is None:
		data={}
		id=request.args['id']
		ids=session['login_id']
		q="select * from users where user_id='%s'"%(id)
		res=select(q)
		data['diet_details']=res
		q="select * from user_diets where user_id='%s'"%(id)
		res1=select(q)
		if res1:
			data['a_diet_details']=res1
		else:
			data['a_diet_details']=""
			

		if 'diets' in request.form:
			user_diet_id=request.form['user_diet_id']
			dietdetails=request.form['dietdetails']
			date=request.form['date']
			q="UPDATE `user_diets` SET `diet_details`='%s',`diet_date`='%s' WHERE `user_diet_id`='%s'"%(dietdetails,date,user_diet_id)
			print(q)
			update(q)
			return redirect(url_for('physician.physician_view_users'))

		if 'diet' in request.form:
			dietdetails=request.form['dietdetails']
			date=request.form['date']
			q="INSERT INTO `user_diets` VALUES(NULL,'%s',(SELECT `physician_id` FROM `physician` WHERE `login_id`='%s'),'%s','%s')"%(id,ids,dietdetails,date)
			insert(q)
			q="INSERT INTO `subscription` VALUES(NULL,'%s',(SELECT `physician_id` FROM `physician` WHERE `login_id`='%s'))"%(id,ids)
			insert(q)
			return redirect(url_for('physician.physician_view_users'))

		return render_template('physician_create_personalised_diet_plan.html',data=data)
	else:
		return redirect(url_for('/.public_home'))

@physician.route('physician_view_message_from_users',methods=['get','post'])
def physician_view_message_from_users():
	if not session.get("login_id") is None:
		data={}
		ids=session['login_id']
		q="select * from message inner join users using(user_id) where physician_id=(select physician_id from physician where login_id='%s')"%(ids)
		print(q)
		res=select(q)
		data['messages']=res
		j=0
		for i in range(1,len(res)+1):
			if 'replys'+str(i) in request.form:
				reply=request.form['reply'+str(i)]
				print(res[j]['message_id'])
				q="update message set message_reply='%s' where message_id='%s'"%(reply,res[j]['message_id'])
				print(q)
				update(q)
				return redirect(url_for('physician.physician_view_message_from_users'))
			j=j+1


		return render_template('physician_view_message_from_users.html',data=data)
	else:
		return redirect(url_for('/.public_home'))