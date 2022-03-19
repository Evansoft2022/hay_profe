# Generated by Django 3.2.8 on 2022-03-16 20:15

from django.db import migrations, models
import django.db.models.deletion


class Migration(migrations.Migration):

    initial = True

    dependencies = [
        ('data', '0001_initial'),
    ]

    operations = [
        migrations.CreateModel(
            name='Teacher',
            fields=[
                ('id', models.BigAutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('documentI', models.CharField(blank=True, default='', max_length=10, null=True)),
                ('first_name', models.CharField(max_length=20)),
                ('surname', models.CharField(max_length=20)),
                ('second_name', models.CharField(blank=True, default='', max_length=20)),
                ('address', models.CharField(max_length=150)),
                ('phone', models.CharField(max_length=10)),
                ('email', models.EmailField(max_length=254, unique=True)),
                ('password', models.CharField(max_length=20)),
                ('block', models.BooleanField(default=True)),
                ('description', models.TextField(blank='', default='')),
                ('img', models.ImageField(default='Photo_Profile_Teacher/photo.jpg', upload_to='Photo_Profile_Teacher')),
                ('type', models.CharField(default='Teacher', max_length=10)),
                ('city', models.ForeignKey(default=1, on_delete=django.db.models.deletion.CASCADE, to='data.city')),
            ],
        ),
        migrations.CreateModel(
            name='Subjects',
            fields=[
                ('id', models.BigAutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('name', models.CharField(max_length=90)),
                ('teacher', models.ForeignKey(default='', on_delete=django.db.models.deletion.CASCADE, to='teacher.teacher')),
            ],
        ),
    ]
