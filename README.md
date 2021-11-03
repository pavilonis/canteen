## Raspberry Pi installation

Download **Raspberry Pi OS Lite** image and write/burn it to SD card   (in my case filename was _2021-05-07-raspios-buster-armhf-lite.img_)  
  
Start raspberry.  
Login with pi:raspberry  
Run commands:  
`sudo apt-get update`  
`sudo apt-get upgrade`  

Install packages needed for displaying UI elements:  
`sudo apt install xorg`

`sudo raspi-config`  
Enable SSH access in **Interface Options -> SSH**   
Change default password in **System Options -> password**  

[Download **Bellsoft Liberica JRE 11 ARM32 FULL**](https://download.bell-sw.com/java/11.0.12+7/bellsoft-jre11.0.12+7-linux-arm32-vfp-hflt-full.deb)  
Install it by running `sudo apt install ./package-file-name.deb`  

create `/opt/canteen` directory and place there configuration file `app.properties` containing properties (change values):  
```
api.auth.username=admin
api.auth.password=admin
api.uri=http://localhost:8080/rest/eating
scanner.port.name=COM3
```

Build project JAR file by running maven `clean` and `package` commands.  
Copy created *.jar file from project's target directory into raspberry pi's /opt/canteen/  

Create systemd service:  
`sudo nano /lib/systemd/system/canteen.service`  

paste the following file contents:
```
[Unit]
Description=Canteen JavaFX application
After=multi-user.target

[Service]
WorkingDirectory=/opt/canteen
ExecStart=sudo java -Dglass.platform=Monocle -jar canteen-1.5.jar

[Install]
WantedBy=multi-user.target   
```

`sudo systemctl daemon-reload`  

Enable application automatic startup  
`sudo systemctl enable canteen.service`  

Save and restart application  
`sudo reboot now`  
