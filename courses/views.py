from django.shortcuts import render
from .models import *

def All_Courses(request):
	courses = Courses.objects.all()
	return render(request,'courses/all-courses.html',{'courses':courses})