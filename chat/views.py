from django.http import HttpResponse
from django.shortcuts import render
from datetime import date, datetime
from .create_zip import Create_Zip
from .models import *
from home.views import SetMoneda
import json
from wallet.models import Wallet

def Inbox(request):
	message = Message_Email.objects.filter(to=request.session['pk'],trash = False).order_by('-time')
	return render(request,'chat/inbox.html',{'message':message})

def Trash(request):
	message = Message_Email.objects.filter(to=request.session['pk'],trash = True).order_by('-time')
	return render(request,'chat/trash.html',{'message':message})

def Email_Sent(request):
	message = Message_Email.objects.filter(From=request.session['pk']).order_by('-time')
	return render(request,'chat/send.html',{'message':message})

def Compose_Email(request,pk):
	if request.is_ajax():
		# print(request.GET.get("message"))
		request.session['data_message'] = str(request.GET.get("message")).strip()
		return HttpResponse(True)
	if request.method == 'POST':
		try:
			user_from = Teacher.objects.get(pk = request.session['pk'])
		except  Teacher.DoesNotExist:
			user_from = Student.objects.get(pk = request.session['pk'])
		now = datetime.now()
		for i in range(len(request.POST.getlist('email'))):
			try:
				user_to = Teacher.objects.get(email = request.POST.getlist('email')[i])
			except  Teacher.DoesNotExist:
				user_to = Student.objects.get(email = request.POST.getlist('email')[i])
			Message_Email(
				From = request.session['pk'],
				to = user_to.pk,
				date = now.today().strftime('%d-%m-%Y %H:%M'),
				time = datetime.now(),
				message = request.session['data_message'],
				from_name = user_from.first_name+' '+user_from.surname,
				to_name = user_to.first_name+' '+user_to.surname,
				subject = request.POST.getlist('subject')[0]
			).save()
		del request.session['data_message']
		if len(request.FILES.getlist('files')) > 0:
			for i in range(len(request.FILES.getlist('files'))):
				Attachments(
					file = request.FILES.getlist('files')[i],
					message_email = Message_Email.objects.filter(pk = request.session['pk']).last()
				).save()
		n = int(request.session['sent']) + 1
		request.session['sent'] = n
	email = ""
	try:
		user_from = Teacher.objects.get(pk = pk)
		if pk != 0:
			email = user_from.email
	except  Teacher.DoesNotExist:
		user_from = None

	try:
		user_from = Student.objects.get(pk = pk)
		if pk != 0:
			email = user_from.email
	except Student.DoesNotExist:
		user_from = None
	
	return render(request,'chat/compose_email.html',{'email':email})

def View_Email(request,pk):
	message = Message_Email.objects.get(pk=pk)
	message.unread = True
	message.save()

	
	try:
		attachments = Attachments.objects.filter(message_email =pk)
	except Attachments.DoesNotExist:
		attachments = None

	if request.is_ajax():
		list_data = [url.file.url for url in attachments ]
		create_z = Create_Zip()
		return HttpResponse(create_z.Run(list_data))
	
	totals = len(attachments) if attachments is not None else 0

	if attachments is not None:
		list_attachments = []
		for i in attachments:
			list_attachments.append({
					'url':i.file.url,
					'name_file' : str(i.file).split('/')[1]
				})




	try:
		user = Teacher.objects.get(pk=message.From)
	except Teacher.DoesNotExist:
		user = Student.objects.get(pk=message.From)
	date = Values_Dates(message.date)
	if len(Values_Dates(message.date)) == 1:
		date = Values_Dates(message.date)[0][:len(Values_Dates(message.date)[0]) - 3]

	return render(request,'chat/view_email.html',{'message':message,'user':user,'date':date,'attachments':list_attachments,
													'totals':totals
												})

def Values_Dates(date_message):
	now = datetime.now()
	fecha_cad1 = now.today().strftime('%d-%m-%Y %H:%M')
	fecha_cad2 = date_message
	fecha1 = datetime.strptime(fecha_cad1, '%d-%m-%Y %H:%M')
	fecha2 = datetime.strptime(fecha_cad2, '%d-%m-%Y %H:%M')
	if str(fecha2 - fecha1).split()[0] == '-1':
		return str(fecha1 - fecha2).split()
	return str(fecha2 - fecha1).split()[0]

def Hours():
	import time
	hours = time.ctime().split()[3].split(':')[0]
	minutos = time.ctime().split()[3].split(':')[1]
	return str(hours)+':'+str(minutos)

def Trash_Email(request):
	message = Message_Email.objects.get(pk=request.GET.get('pk'))
	message.trash = True
	message.save()
	return HttpResponse("Hola")

def Delete_Email(request):
	Message_Email.objects.get(pk=request.GET.get('pk')).delete()
	return HttpResponse("Hola")	

def Chat_Private(request):
	try:
		with open(r'C:\Users\Lenovo\Desktop\hay_profe-main\static\data_user.json','r') as file:
			print(json.loads(file.read()))
	except Exception as e:
		pass
	


	obj = Obj(request)[0]
	try:
		user = Teacher.objects.get(pk=request.session['pk'])
	except Teacher.DoesNotExist:
		user = Student.objects.get(pk=request.session['pk'])

	with open(r'C:\Users\Lenovo\Desktop\hay_profe-main\static\data_user.json','w') as file:
		x = {
			'id': user.pk,
			'name': str(obj.first_name)+' '+str(obj.surname),
			'number': str(obj.phone),
			'pic': 'http://localhost:8000'+str(obj.img.url)
		}
		json.dump(x,file)


	if request.is_ajax():
		if request.GET.get('pk'):
			data = json.loads(request.GET.get('pk'))
			Chat_Privates(
				sender = data['sender'],
				body = data['body'],
				time = data['time'],
				status = data['status'],
				recvId = data['recvId'],
				_id = data['id'],
				_from= 1,
				_to = 2
			).save()
			with open(r"C:\Users\Lenovo\Desktop\hay_profe-main\static\message.json",'r') as file:
				data_message = json.loads(file.read())
			x = data_message
			consec = len(Chat_Privates.objects.all())
			data = {
						"id":consec,
						"sender": data['sender'], 
						"body": data['body'], 
						"time": data['time'], 
						"status": data['status'], 
						"recvId": data['recvId'], 
						"recvIsGroup": False
					}
			z = x.append(data)
			with open(r"C:\Users\Lenovo\Desktop\hay_profe-main\static\message.json",'w') as file:
				json.dump(x,file)

			return HttpResponse("Hola bebe")
		elif request.GET.get("recibe"):
			n = int(request.session['time_chat']) + 1
			print(request.GET)
			if request.GET.get("recibe"):

				try:
					user = Teacher.objects.get(pk=request.session['pk'])
				except Exception:
					user = Student.objects.get(pk=request.session['pk'])
				print(user.type)
				if str(user.type) != 'Teacher':
					#RECIBE EL DINERO EL PROFESOR
					wallet_recvi = Wallet.objects.get(user = Teacher.objects.get(pk=request.GET.get('recibe')))
					value_money = Teacher.objects.get(pk=request.GET.get('recibe'))
					print(float( str(wallet_recvi.amount).replace(',','') ))
					add = float( str(wallet_recvi.amount).replace(',','') ) + float(value_money.money)
					wallet_recvi.amount = SetMoneda(str(add), simbolo="US$", n_decimales=0)
					wallet_recvi.save()
					print(value_money)
					
					
					#PAGA EL ESTUDIANTE
					wallet_sender = Wallet.objects.get(user = Student.objects.get(email = request.session['student_email']))
					less = float( str(wallet_sender.amount).replace(',','') ) - float(value_money.money)
					discount = less
					wallet_sender.amount = SetMoneda(str(less), simbolo="US$", n_decimales=0)
					wallet_sender.save()
					request.session['amount_wallet'] = discount

			else:
				print("Error")

			request.session['time_chat'] = n

			return HttpResponse(request.session['time_chat'])

	try:
		user = Teacher.objects.get(pk=request.session['pk'])
	except Teacher.DoesNotExist:
		user = Student.objects.get(pk=request.session['pk'])
	return render(request,'chat/chat_private.html',{'user':user,'x':{"Hola":'Hola'}})

def Obj(request):
	_type = ""
	try:
		user = Teacher.objects.get(pk=request.session['pk'])
		_type = "teacher"
	except Teacher.DoesNotExist:
		user = Student.objects.get(pk=request.session['pk'])
		_type = "student"
	return [user,_type]




