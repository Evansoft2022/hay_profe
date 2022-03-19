from django.db import models

class Wallet(models.Model):
	user = models.EmailField()
	amount = models.CharField(max_length=10)

