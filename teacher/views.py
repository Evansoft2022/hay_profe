from django.shortcuts import render,redirect
from .models import Teacher
import env, json


def All_Teacher(request):
	techers = Teacher.objects.all()
	return render(request,'teacher/all-professors.html',{'teacher':techers})

def Teacher_Profile(request,pk):
	teacher = Teacher.objects.get(pk = pk)
	subject = Subjects.objects.filter(teacher = teacher)
	return render(request,'teacher/professor-profile.html',{'teacher':teacher,'subject':subject})

def Sent_Message(request,pk):
	with open(env.url['message'],'r') as file:
		data = json.loads(file.read())
	x = data
	print(x)
	y = x
	y.append({
				"id": 1, 
				"sender": int(pk), 
				"body": "Hola, en que te puedo ayudar?", 
				"time": "Feb 01, 2022 17:23:01", 
				"status": 1, 
				"recvId": request.session['pk'],
				"recvIsGroup": False
			})

	with open(env.url['message'],'w') as file_json:
		json.dump(y,file_json)

	return redirect('Chat_Private')