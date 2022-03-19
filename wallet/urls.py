from django.conf.urls import url
from .views import *

urlpatterns=[
	url(r'^Method_Paymeth/$',Method_Paymeth,name="Method_Paymeth"),
]