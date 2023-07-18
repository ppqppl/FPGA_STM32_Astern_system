import serial
ser = serial.Serial('/dev/ttyUSB0', 115200)
if ser.isOpen:
    print("aaa")
while True:
    data = ser.readline().decode('utf-8').strip()
    print(data)
