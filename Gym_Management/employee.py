from flask import Flask,Blueprint,redirect,url_for,render_template,request,session
from database import*
import uuid

employee=Blueprint('employee',__name__)

@employee.route('employee_home',methods=['get','post'])
def employee_home():
	if not session.get("login_id") is None:

		return render_template('employee_home.html')
	else:
		return redirect(url_for('/.public_home'))

@employee.route('employee_manage_equipments',methods=['get','post'])
def employee_manage_equipments():
	if not session.get("login_id") is None:
		data={}
		if 'action' in request.args:
			action=request.args['action']
			id=request.args['id']
		else:
			action=None
		print(action)
		if action=="delete":
			q="DELETE FROM `equipments` WHERE `equipment_id`='%s'"%(id)
			delete(q)
		if action=="update":
			q="select * from equipments where equipment_id='%s'"%(id)
			res=select(q)
			data['update_equipments']=res
		if 'uequipment' in request.form:
			ueqname=request.form['ueqname']
			qnty=request.form['qnty']
			udescription=request.form['udescription']
			uimage=request.files['uimage']
			path='static/img/'+str(uuid.uuid4())+uimage.filename
			uimage.save(path)
			q="update equipments set name='%s',description='%s',image='%s',qnty='%s' where equipment_id='%s'"%(ueqname,udescription,path,qnty,id)
			update(q)
			return redirect(url_for('employee.employee_manage_equipments'))

		if 'equipment' in request.form:
			eqname=request.form['eqname']
			description=request.form['description']
			qnty=request.form['qnty']
			image=request.files['image']
			path='static/img/'+str(uuid.uuid4())+image.filename
			image.save(path)
			q="INSERT INTO `equipments` VALUES(NULL,'%s','%s','%s','%s')"%(eqname,description,path,qnty)
			insert(q)

		q="select * from equipments"
		res=select(q)
		data['equipments_details']=res

		return render_template('employee_manage_equipments.html',data=data)
	else:
		return redirect(url_for('/.public_home'))

@employee.route('employee_manage_workout_details',methods=['get','post'])
def employee_manage_workout_details():
	if not session.get("login_id") is None:
		data={}

		if 'action' in request.args:
			action=request.args['action']
			id=request.args['id']
		else:
			action=None
		print(action)
		if action=="delete":
			q="DELETE FROM `workouts` WHERE `workout_id`='%s'"%(id)
			delete(q)
		if action=="update":
			q="select * from workouts where workout_id='%s'"%(id)
			res=select(q)
			data['update_workouts']=res
			q="select equipment_id,name,(equipment_id='%s') as sel from equipments order by sel desc,equipment_id asc"%(res[0]['equipment_id'])
			res=select(q)
			data['uview_equipments']=res
		if 'uworkout' in request.form:
			utitle=request.form['utitle']
			uequipment_id=request.form['uequipment_id']
			udescription=request.form['udescription']
			ubenefits=request.form['ubenefits']
			q="UPDATE `workouts` SET `title`='%s',equipment_id='%s',`description`='%s',`benefits`='%s' WHERE `workout_id`='%s'"%(utitle,uequipment_id,udescription,ubenefits,id)
			update(q)
			return redirect(url_for('employee.employee_manage_workout_details'))
		if 'workout' in request.form:
			title=request.form['title']
			equipment_id=request.form['equipment_id']
			description=request.form['description']
			benefits=request.form['benefits']
			q="INSERT INTO `workouts` VALUES(NULL,'%s','%s','%s','%s')"%(equipment_id,title,description,benefits)
			insert(q)

		q="SELECT * FROM workouts INNER JOIN `equipments` USING(`equipment_id`)"
		res=select(q)
		data['workout_details']=res
		q="SELECT * FROM `equipments`"
		res=select(q)
		data['view_equipments']=res

		return render_template('employee_manage_workout_details.html',data=data)
	else:
		return redirect(url_for('/.public_home'))
	
@employee.route('employee_personalised_workout_details',methods=['get','post'])
def employee_personalised_workout_details():
	if not session.get("login_id") is None:
		data={}
		id=request.args['id']


		q="SELECT * FROM `workouts`"
		res=select(q)
		data['view_workouts']=res
		q="SELECT * FROM `users` WHERE user_id='%s'"%(id)
		res=select(q)
		data['user_workout_details']=res
		q="select * from user_workouts where user_id='%s'"%(id)
		res1=select(q)
		if res1:
			data['p_workout_details']=res1
			q="select workout_id,title,(workout_id='%s')as sel from workouts order by sel desc,workout_id asc"%(res1[0]['workout_id'])
			res=select(q)
			data['v_view_workouts']=res
		else: 
			data['p_workout_details']=""
		

		if 'workout' in request.form:
			user_id=request.form['user_id']
			workout_id=request.form['workout_id']
			day=request.form['day']
			duration=request.form['duration']
			q="INSERT INTO `user_workouts` VALUES(NULL,'%s','%s','%s','%s')"%(user_id,workout_id,day,duration)
			insert(q)

			return redirect(url_for('employee.employee_add_user_details'))

		if 'workouts' in request.form:
			workout_id=request.form['workout_ids']
			day=request.form['day']
			duration=request.form['duration']
			q="update user_workouts set workout_id='%s',day='%s',duration='%s' where user_id='%s'"%(workout_id,day,duration,id)
			update(q)

			return redirect(url_for('employee.employee_add_user_details'))
		

		return render_template('employee_personalised_workout_details.html',data=data)
	else:
		return redirect(url_for('/.public_home'))

@employee.route('employee_add_user_details',methods=['get','post'])
def employee_add_user_details():
	if not session.get("login_id") is None:
		data={}
		q="SELECT *,users.phone as uphone,users.email as uemail FROM `users` INNER JOIN `batches` USING(`batch_id`) INNER JOIN `gym_instructor` USING(`instructor_id`)"
		res=select(q)
		data['view_user']=res

		return render_template('employee_add_user_details.html',data=data)
	else:
		return redirect(url_for('/.public_home'))

@employee.route('employee_add_attendance_details',methods=['get','post'])
def employee_add_attendance_details():
	if not session.get("login_id") is None:
		data={}
		from datetime import datetime

		current_date = datetime.today()
		current_date_str = current_date.strftime('%Y-%m-%d')

		print(current_date_str)

		q="select * from users"
		res=select(q)
		data['user_details']=res
		q="select * from attendance inner join users using(user_id)"
		res=select(q)
		data['attendance_details']=res
		if 'action' in request.args:
				action=request.args['action']
				id=request.args['id']
		else:
			action=None
		print(action)
		if action=="delete":
			q="delete from attendance where attendance_id='%s'"%(id)
			delete(q)
			return redirect(url_for('employee.employee_add_attendance_details'))
		if 'attendance' in request.form:
			user_id=request.form['user_id']
			date=request.form['date']
			stime=request.form['stime']
			etime=request.form['etime']
			q="insert into attendance values(null,'%s','%s','%s','%s')"%(user_id,date,stime,etime)
			insert(q)
			return redirect(url_for('employee.employee_add_attendance_details'))

		return render_template('employee_add_attendance_details.html',data=data,current_date_str=current_date_str)
	else:
		return redirect(url_for('/.public_home'))


@employee.route('employee_view_complaint',methods=['get','post'])
def employee_view_complaint():
	if not session.get("login_id") is None:
		data={}

		q="select * from complaints inner join users using(user_id)"
		res=select(q)
		data['complaints']=res
		j=0
		for i in range(1,len(res)+1):
			if 'replys'+str(i) in request.form:
				reply=request.form['reply'+str(i)]
				print(res[j]['complaint_id'])
				q="update complaints set reply='%s' where complaint_id='%s'"%(reply,res[j]['complaint_id'])
				print(q)
				update(q)
				return redirect(url_for('employee.employee_view_complaint'))
			j=j+1
			

		return render_template('employee_view_complaint.html',data=data)
	else:
		return redirect(url_for('/.public_home'))