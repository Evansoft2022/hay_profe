from django.http import HttpResponse
from django.shortcuts import render,redirect
from .models import *
from home.views import SetMoneda
from data.models import Consecutive

def Method_Paymeth(request):
	consec = Consecutive.objects.last()

	if request.is_ajax():
		amount = request.GET.get("amount")
		wallet = Wallet.objects.get(user = request.session['student_email'])
		wallet.amount = amount
		wallet.save()
		wallet_amount = request.session['amount_wallet'].replace(',','')
		n = float(wallet_amount) + float(amount)
		request.session['amount_wallet'] = SetMoneda(str(n), simbolo="US$", n_decimales=0)
		c = int(consec.number) + 1
		consec.number = c
		consec.save()
		return HttpResponse(n)

	return render(request,'wallet/payment_method.html',{'number':consec.number})
