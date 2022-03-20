from teacher.models import Teacher
from student.models import Student
from django.shortcuts import render

class Register_ROL:
	def __str__(self):
		pass

	def Suscription(self,value):
		print(value)

	def Save_Data(self,value,args):
		value = ""
		if args[-1] == 'on':
			self.Suscription(args[4])
		if value == 'Profesor':
			value = self.Register_Teacher(args)
		else:
			value = self.Register_Student(args)



	def Register_Student(self,args):
		try:
			Student(
				first_name = args[1],
				surname = args[2],
				address = args[7],
				phone= args[6],
				email = args[4],
				password = args[3]
			).save()
		except Exception as e:
			return None
		return 1
		


	def Register_Teacher(self,args):
		try:
			Teacher(
				first_name = args[1],
				surname = args[2],
				address = args[7],
				phone= args[6],
				email = args[4],
				password = args[3],
				money = args[8],
				number_bank = args[9]
			).save()
		except Exception as e:
			return None
		return 1
		


