from django.http import HttpResponse
from django.shortcuts import render,redirect
from .register import Register_ROL
from teacher.models import *
from student.models import Student
from chat.models import Message_Email
from data.models import *
from wallet.models import *
import env

def SetMoneda(num, simbolo="US$", n_decimales=0):
    """Convierte el numero en un string en formato moneda
    SetMoneda(45924.457, 'RD$', 2) --> 'RD$ 45,924.46'
    """
    #con abs, nos aseguramos que los dec. sea un positivo.
    n_decimales = abs(n_decimales)

    #se redondea a los decimales idicados.
    # num = round(num, n_decimales)

    #se divide el entero del decimal y obtenemos los string
    num, dec = str(num).split(".")

    #si el num tiene menos decimales que los que se quieren mostrar,
    #se completan los faltantes con ceros.
    dec += "0" * (n_decimales - len(dec))

    #se invierte el num, para facilitar la adicion de comas.
    num = num[::-1]

    #se crea una lista con las cifras de miles como elementos.
    l = [num[pos:pos+3][::-1] for pos in range(0,50,3) if (num[pos:pos+3])]
    l.reverse()

    #se pasa la lista a string, uniendo sus elementos con comas.
    num = str.join(",", l)

    #si el numero es negativo, se quita una coma sobrante.
    try:
        if num[0:2] == "-,":
            num = "-%s" % num[2:]
    except IndexError:
        pass

    #si no se especifican decimales, se retorna un numero entero.
    if not n_decimales:
        return "%s" % (num)

    return "%s.%s" % (num, dec)



def Login(request):
	error = False
	request.session['time_chat'] = 0
	if request.method == 'POST':
		try:
			if validate_teacher(request,request.POST.get("email"),request.POST.get("pass")) is not None:
				request.session['teacher_email'] = request.POST.get("email")
			elif validate_student(request,request.POST.get("email"),request.POST.get("pass")) is not None:
				request.session['student_email'] = request.POST.get("email")
		except Exception as e:
			error = True
			return render(request,'Home/login.html',{'error':error})
		wallet = Wallet.objects.get(user=request.POST.get("email"))
		request.session['amount_wallet'] = SetMoneda(str(float(str(wallet.amount).replace(",",''))), simbolo="US$", n_decimales=0)
		return redirect('Home')
	return render(request,'Home/login.html',{'error':error})

def validate_teacher(request,email,password):
	try:
		user = Teacher.objects.get(email = email, password = password)
		request.session['name'] = user.first_name+' '+user.surname
		request.session['pk'] = user.pk
		request.session['type'] = "teacher"
	except Teacher.DoesNotExist:
		user = None
	return user

def validate_student(request,email,password):
	try:
		user = Student.objects.get(email = email, password = password)
		request.session['name'] = user.first_name+' '+user.surname
		request.session['pk'] = user.pk
		request.session['type'] = "student"
	except Teacher.DoesNotExist:
		user = None
	return user

def Delete_Session(request):
	del request.session['name']
	del request.session['pk']
	del request.session['img_profile']
	del request.session['amount_wallet']



def Add_Contact(user,rol):
	import json
	with open(env.url['contact'],'r') as file:
		information_contact = json.loads(file.read())
	x = information_contact
	z = x
	y = z.append(
		{
			"id": user.pk,
			"name": user.first_name+' '+user.surname,
			"number": user.phone,
			"pic": "http://localhost:8000"+str(user.img.url),
			"lastSeen": "Apr 29 2022 17:58:02",
			"type": rol
		}
	)
	with open(env.url['contact'], 'w') as file:
		json.dump(z,file)

def Register(request):
	error = False
	request.session['time_chat'] = 0
	if request.method == 'POST':
		list_data = []
		for i in request.POST:
			list_data.append(request.POST.get(i))
		print(list_data)
		r = Register_ROL()
		r = r.Save_Data(request.POST.get("rol"),list_data)
		if r is not None:
			request.session['name'] = request.POST.get("first_name")+' '+request.POST.get("surname")
			if request.POST.get("rol") == "Profesor":
				users = Teacher.objects.get(email = request.POST.get("email"))
				request.session['teacher_email'] = request.POST.get("email")
				request.session['pk'] = Teacher.objects.get(email=request.POST.get("email")).pk
				request.session['img_profile'] = Teacher.objects.get(email=request.POST.get("email")).img.url
				request.session['type'] = "teacher"
				Wallet(
					user = request.POST.get("email"),
					amount = 0
				).save()
				Add_Contact(users,request.POST.get("rol"))
			else:
				users = Student.objects.get(email = request.POST.get("email"))
				request.session['student_email'] = request.POST.get("email")
				request.session['pk'] = Student.objects.get(email=request.POST.get("email")).pk
				request.session['img_profile'] = Student.objects.get(email=request.POST.get("email")).img.url
				request.session['type'] = "student"
				Wallet(
					user = request.POST.get("email"),
					amount = 0
				).save()
				Add_Contact(users,request.POST.get("rol"))
			request.session['sent'] = 0
			request.session['inbox'] = 0
			request.session['amount_wallet'] = 0
			return redirect("Home")
		else:
			error = True
	city = City.objects.all()

	return render(request,'Home/register.html',{'city':city,'error':error})

def UnLock(request):
	if request.method == 'POST':
		if 'teacher_email' in request.session:
			try:
				user = Teacher.objects.get(password = request.POST.get("pass"))
				request.session['name'] = user.first_name+' '+user.surname
				request.session['pk'] = user.pk
				request.session['teacher_email'] = user.email
				return redirect("Home")
			except Teacher.DoesNotExist:
				return render(request,'Home/lock.html')

		if 'student_email' in request.session:
			try:
				user = Student.objects.get(password = request.POST.get("pass"))
				request.session['name'] = user.first_name+' '+user.surname
				request.session['pk'] = user.pk
				request.session['student_email'] = user.email
				return redirect("Home")
			except Student.DoesNotExist:
				return render(request,'Home/lock.html')
	return redirect("/")




def LogOut(request):
	if 'teacher_email' in request.session:
		del request.session['teacher_email']
	if 'student_email' in request.session:
		del request.session['student_email']
	if 'img_profile' in request.session:
		del request.session['img_profile']
	del request.session['pk']
	return redirect('Login')

def Home(request):
	if 'teacher_email' in request.session:
		user = Teacher.objects.get(email = request.session['teacher_email'])
		request.session['img_profile'] = user.img.url
		request.session['sent'] = len(Message_Email.objects.filter(From = request.session['pk']))
		request.session['inbox'] = len(Message_Email.objects.filter(to = request.session['pk']))
	elif 'student_email' in request.session:
		user = Student.objects.get(email = request.session['student_email'])
		request.session['img_profile'] = user.img.url
		request.session['sent'] = len(Message_Email.objects.filter(From = request.session['pk']))
		request.session['inbox'] = len(Message_Email.objects.filter(to = request.session['pk']))


	return render(request,'Home/index.html')

def Setting_Profile(request,pk):
	if Validate_Session(request):
		value = False
		if 'teacher_email' in request.session:
			value = True

		if request.method == 'POST':
			img = request.FILES.get('photo_profile')

			pk = request.POST.get('pk')
			if 'teacher_email' in request.session:
				teacher = Teacher.objects.get(pk = pk)
				teacher.img = img
				teacher.save()
				request.session['img_profile'] = teacher.img.url
			else:
				student = Student.objects.get(pk = pk)
				subject.img = img
				subject.save()
				request.session['img_profile'] = student.img.url

		city = City.objects.all()

		if value:
			try:
				teacher = Teacher.objects.get(pk = pk)
				subject = Subjects.objects.filter(teacher = teacher)
				return render(request,'teacher/professor-profile.html',{'teacher':teacher,'subject':subject,'city':city})
			except Teacher.DoesNotExist:
				Delete_Session(request)
				return render(request,'Home/lock.html')
		else:
			try:
				student = Student.objects.get(pk = pk)
				return render(request,'student/student-profile.html',{'student':student,'city':city})
			except Student.DoesNotExist:
				Delete_Session(request)
				return render(request,'Home/lock.html')
	return redirect("/")


def Validate_Session(request):
	if 'pk' in request.session:
		return True
	return False

































