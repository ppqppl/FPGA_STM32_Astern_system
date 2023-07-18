# ! /usr/bin/env python3
# -*- coding: utf-8 -*-
import _thread
import time
import datetime
import sys
import socket
import serial
# import glob
import serial.tools.list_ports
import main
from PyQt5 import QtWidgets

COM_Band = ["2400", "4800", "9600", "19200", "38400", "57600", "115200", "128000"]
custom_serial = serial.Serial

# 1.创建一个udp套接字
udp_send_socket = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
udp_recv_socket = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)

if_time = False
if_show = True
if_sql = False
if_fpga = False # False 就是 STM True 就是 FPGA

if_com_open = False
if_udp_open = False


def udp_send(disstr):
    # 1.创建一个udp套接字
    udp_socket1 = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)

    # 2.准备接收方的地址
    # 192.168.65.149 表示目的地ip
    # 30000  表示目的地端口
    udp_socket1.sendto(disstr.encode("utf-8"), (window.ipedit.text(), int(window.portedit.text())))

    # 3.关闭套接字
    udp_socket1.close()

def udp_recv(threadName):
    global if_show
    global if_time
    global if_udp_open
    if if_fpga:
        print("硬件开发中，下面给出的仍是STM32版本")
        olddata = "a"
        newdata = "a"
        datastr = ""
        while True:
            if if_udp_open:
                try:
                    recv_data, remote_addr = udp_recv_socket.recvfrom(1024)
                    # print("udp:  ",recv_data[0].decode('utf-8')[0:7])
                    data = str(recv_data, 'utf-8')
                    print("udp:  ",data)
                    ip = remote_addr[0]
                    port = remote_addr[1]
                    window.stm32_udp_display(data,ip,port)
                except socket.timeout:
                    # udp_recv_socket.close()
                    # window.ip_noedit.clear()
                    # window.port_noedit.clear()
                    print("关闭udp")
                    if if_show:
                        if if_time:
                            time_now = get_datetime()
                            window.udp_text.append(time_now+":  ")
                        window.udp_text.append("获取数据超时！")
                    window.udpconnect_btn.setText("开启udp")
                    window.udpmode_edit.clear()
                    window.ipedit.setEnabled(True)
                    window.portedit.setEnabled(True)
                    if_udp_open = False
                if if_udp_open == False:
                    window.udptime_edit.setEnabled(True)
                    udp_recv_socket.close()
                    window.ip_noedit.clear()
                    window.port_noedit.clear()
    else:
        while True:
            if if_udp_open:
                try:
                    recv_data, remote_addr = udp_recv_socket.recvfrom(1024)
                    # print("udp:  ",recv_data[0].decode('utf-8')[0:7])
                    data = str(recv_data, 'utf-8')
                    print("udp:  ",data)
                    ip = remote_addr[0]
                    port = remote_addr[1]
                    window.stm32_udp_display(data,ip,port)
                except socket.timeout:
                    # udp_recv_socket.close()
                    # window.ip_noedit.clear()
                    # window.port_noedit.clear()
                    print("关闭udp")
                    if if_show:
                        if if_time:
                            time_now = get_datetime()
                            window.udp_text.append(time_now+":  ")
                        window.udp_text.append("获取数据超时！")
                    window.udpconnect_btn.setText("开启udp")
                    window.udpmode_edit.clear()
                    window.ipedit.setEnabled(True)
                    window.portedit.setEnabled(True)
                    if_udp_open = False
                if if_udp_open == False:
                    window.udptime_edit.setEnabled(True)
                    udp_recv_socket.close()
                    window.ip_noedit.clear()
                    window.port_noedit.clear()


# 获取串口列表
def Get_Com_List():
    return list(serial.tools.list_ports.comports())
    # return list(glob.glob('/dev/tty*'))



def get_datetime():
    # 获取当前时间
    now = datetime.datetime.now()
    # 将 datetime.datetime 对象转换为字符串
    str_now = now.strftime('%Y-%m-%d %H:%M:%S')
    return str_now

def com_recv(threadName):
    while True:
        if if_fpga:
            num_all = 0
            char = ""
            while if_com_open:
                data = custom_serial.read_all()
                if data == b'':
                    continue
                else :
                    char = str(data, 'utf-8')
                    print("com:  ", char)
                    if char == '\r\n':
                        break
            while if_com_open:
                if custom_serial.in_waiting:  # 如果串口接收到了数据
                    data = custom_serial.read(custom_serial.in_waiting)  # 读取所有可用的数据
                    num_chars = len(data)  # 获取收到的字符个数
                    num_all = num_all + num_chars
                    print(data)
                    print(num_all)
                    if num_all <= 7:
                        char = char + str(data, encoding="utf-8")
                    elif num_all == 11:
                        # distance = float(char)
                        distance = float(char)
                        # disstr = char
                        # udp_send(disstr)
                        # print(char[0:7])
                        print("距离：", distance, "cm")
                        window.fpga_com_display(distance)
                        char = ""
                        num_all = 0
        else :
            while if_com_open:
                data = custom_serial.read_all()
                if data == b'':
                    continue
                else:
                    # char = str(data)
                    char = str(data, 'utf-8')
                    print("com:  ", char)
                    window.stm32_com_display(char)

# def judge_device(threadName):
#     global if_fpga
#     while True:
#         if window.fpga_radiobtn.isChecked():
#             if if_fpga == False:
#                 print("fpga")
#             if_fpga = True
#         elif window.stm_radiobtn.isChecked():
#             if if_fpga:
#                print("STM32")
#             if_fpga = False

class Mywindow(QtWidgets.QMainWindow, main.Ui_MainWindow):
    def __init__(self):
        super(Mywindow, self).__init__()
        self.setupUi(self)
        COM_List = Get_Com_List()  # 获取串口列表
        for i in range(0, len(COM_List)):  # 将列表导入到下拉框
            self.com_combox.addItem(COM_List[i].name)
        self.bit_combox.addItems(COM_Band)
        self.bit_combox.setCurrentIndex(6)

    def consearch_btn_click(self):
        print("搜索串口")
        COM_List = Get_Com_List()  # 获取串口列表
        self.com_combox.clear()
        for i in range(0, len(COM_List)):  # 将列表导入到下拉框
            self.com_combox.addItem(COM_List[i].name)

    def comopen_btn_click(self):    # 打开串口
        global if_com_open
        global custom_serial
        global if_fpga
        if window.fpga_radiobtn.isChecked():
            if_fpga = True
            print("fpga")
        elif window.stm_radiobtn.isChecked():
            if_fpga = False
            print("STM32")
        if self.comopen_btn.text() == "打开串口":
            self.comopen_btn.setText("关闭串口")
            print("打开串口")
            # custom_serial = serial.Serial("/dev/"+self.com_combox.currentText(), int(self.bit_combox.currentText()), timeout=0.5)
            custom_serial = serial.Serial(self.com_combox.currentText(), int(self.bit_combox.currentText()), timeout=0.5)
            if custom_serial.isOpen():
                print("串口打开成功")
                if_com_open = True
                self.com_combox.setEnabled(False)
                self.bit_combox.setEnabled(False)
                if self.fpga_radiobtn.isChecked():
                    self.commode_edit.setText("FPGA")
                else :
                    self.commode_edit.setText("STM32")
            else :
                if_com_open = False
                if_com_open = ~if_com_open
                self.comopen_btn.setText("打开串口")
                print("串口打开失败")
                self.com_combox.setEnabled(True)
                self.bit_combox.setEnabled(True)
                self.commode_edit.clear()
        elif self.comopen_btn.text() == "关闭串口":
            if_com_open = False
            self.comopen_btn.setText("打开串口")
            custom_serial.close()
            self.com_combox.setEnabled(True)
            self.bit_combox.setEnabled(True)
            self.commode_edit.clear()

    def udpconnect_btn_click(self):
        global if_udp_open
        global udp_recv_socket
        if self.udpconnect_btn.text() == "开启udp":
            print("开启udp")
            udp_recv_socket = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
            local_addr = ('', int(self.portedit.text()))
            udp_recv_socket.bind(local_addr)
            udp_recv_socket.settimeout(float(self.udptime_edit.text()))
            self.udpconnect_btn.setText("关闭udp")
            if_udp_open = True
            self.ipedit.setEnabled(False)
            self.portedit.setEnabled(False)
            self.udptime_edit.setEnabled(False)
            if self.fpga_radiobtn.isChecked():
                self.udpmode_edit.setText("FPGA")
            else:
                self.udpmode_edit.setText("STM32")
        elif self.udpconnect_btn.text() == "关闭udp":
            print("关闭udp")
            # udp_recv_socket.close()
            self.udpconnect_btn.setText("开启udp")
            if_udp_open = False
            self.ipedit.setEnabled(True)
            self.portedit.setEnabled(True)
            self.udptime_edit.setEnabled(True)
            self.udpmode_edit.clear()


    def udpsend_btn_click(self):
        print("udp发送")
        disstr = self.send_text.toPlainText()
        print(disstr)
        global udp_socket
        # 2.准备接收方的地址
        # 192.168.65.149 表示目的地ip
        # 30000  表示目的地端口
        udp_send_socket.sendto(disstr.encode("utf-8"), (self.ipedit.text(), int(self.portedit.text())))
        # 3.关闭套接字
        udp_send_socket.close()

    def comsend_btn_click(self):
        print("串口发送")

    def clear_btn_click(self):
        print("清空文本")
        self.udp_text.clear()
        self.com_text.clear()
        self.ip_noedit.clear()
        self.port_noedit.clear()

    def time_checkbox_toggle(self):
        global if_time
        if_time = ~if_time
        if if_time:
            print("日志模式")

    def show_checkBox_toggle(self):
        global if_show
        if if_show == False:
            if_show = True
        elif if_show == True:
            if_show = False
            print("停止显示")

    def sql_checkbox_toggle(self):
        global if_sql
        if_sql = ~if_sql
        if if_sql:
            print("存储到数据库")

    def stm32_com_display(self,data):
        if if_show:
            if if_time:
                time_now = get_datetime()
                self.com_text.append(time_now+":  ")
            self.com_text.append(data)
            self.com_text.moveCursor(self.com_text.textCursor().End)

    def fpga_com_display(self,data):
        if if_show:
            if if_time:
                time_now = get_datetime()
                self.com_text.append(time_now+":  ")
            self.com_text.append(data)

    def stm32_udp_display(self,data,ip,port):
        if if_show:
            if if_time:
                time_now = get_datetime()
                self.udp_text.append(time_now+":  ")
            self.udp_text.append(data)
            self.udp_text.moveCursor(self.udp_text.textCursor().End)
            self.ip_noedit.setText(ip)
            self.port_noedit.setText(str(port))

if __name__ == '__main__':
    app = QtWidgets.QApplication(sys.argv)
    window = Mywindow()
    window.show()
    _thread.start_new_thread(com_recv, ("Thread-1",))
    _thread.start_new_thread(udp_recv, ("Thread-2",))
    # _thread.start_new_thread(judge_device, ("Thread-2",))
    sys.exit(app.exec_())
