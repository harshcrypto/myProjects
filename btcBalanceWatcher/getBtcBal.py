import requests
import csv

with open('file-list.txt') as csv_file:
    file_list_reader = csv.reader(csv_file, delimiter=',')
    file_count = 0
    totalBalance=0
    for row in file_list_reader:
        fileName=row[0]
        print('Processing File:' ,fileName)
        with open(fileName) as csv_file:
            csv_reader = csv.reader(csv_file, delimiter=',')
            line_count = 0
            totalWalletBalance=0
            for row in csv_reader:
                if line_count == 0:
                    line_count += 1
                else:
                    address=row[0]
                    balance=float(row[1])
                    url = "https://blockexplorer.com/api/addr/"+address+"/balance"
                    resp = requests.get(url)
                    currentBalance = float(resp.text)
                    if balance != currentBalance:
                        print('\t**************************Hacked address:', address,' original balance', balance ,'currentBalance', currentBalance)
                    print('\tSafe address:', address,' original balance', balance ,'currentBalance', currentBalance)
                    totalWalletBalance+=float(balance)
                    line_count += 1
            print("Total Balance:",totalWalletBalance/100000000)
            print('Processed ',line_count-1,' addresses.\n')
        file_count += 1
        totalBalance+=totalWalletBalance

print('Processed', file_count,' files.')
print("Total Balance:",totalBalance/100000000)

spoofAddress = open('spoofAddress.txt')

spoofLine = spoofAddress.readline()
while spoofLine:
    #print("Spoof Address:"+ spoofLine)
    r = requests.get("https://blockexplorer.com/api/addr/"+spoofLine+"/balance")
    spoofLine = spoofAddress.readline()
spoofAddress.close()


