from django.shortcuts import render
from .models import Student

def All_Students(request):
	student = Student.objects.all()
	return render(request,'student/all-students.html',{'student':student})

def Profile_Student(request,pk):
	student = Student.objects.get(pk = pk)
	return render(request,'student/student-profile.html',{'student':student})
