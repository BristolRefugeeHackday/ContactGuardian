# LOGIC FLOW
Enacted when the user calls the service. Aim is for them to record a message to be sent to their saved contacts

Text formatted as a code block represents an insructuon to the user.
Text formatted as a blockqoute represents a user input. 




## Step 1
`Hello. This is Contact Guardian. Please enter your pin.`
> 123456

### Response
File: `pin-received.php`

#### If pin is recognised
Hello <Username>. You have 3 contacts saved.

#### Else if pin is not recognised
Sorry, I didn't recognise that number.




## Step 2
```
To call a single contact, press 1. 
To alert all your contacts press 2`
```

### Response 
File: TBC 

#### If receive 1 (i.e. call contact)
Go to step 3A




## Step 3A (Call a contact)
```
To call Nigel, press 1. 
To call Silvia, press 2
To call Light, press 3
To start again, press 0
```
### Response 
File: TBC 

#### If response in [1,2,3]
Retreive the number of the contact and call.

#### If response == 0
Go to step 1




## Step 3B (alert contacts)

```
We can let your contacts know how to call you.
If you can be called on this number, press 1
If you want to enter another number, press 2
To send a message without a number, press 3
```

### Response 
File: TBC 

#### If response == 1
Obtain the number that they are being called on.
Go to step 5

#### If response == 2
Go to step 4

#### If response == 2
Go to step 5




## Step 4 (Enter a new number)
`Please type your phone number in the telephone keypad`

### Response 
File: TBC 

> 07855 369 483

#### For any response
```
The number you have is 0 7 8 5 5 3 6 9 4 8 3`
If this is correct, press 1
To try again, press 2
```

#### If response == 1
Save the number. 
Go to step 5

#### If response == 2
Go to step 4




## Step 5 (Record a message and notify contacts a new number)
To record a message to send to your contacts, press 1
To send your number without a message, press 2

### Response 
File: TBC 

#### If response == 1
`Start speaking at the beep. When you are finished press star.`
> Record audio
> Press *
Save this as a file
Go to step 6

#### If response == 2
Go to step 6




# Step 6
```
Thank you. Your message and phone number has been sent to Nigel, Silvia, and Light. 
We will send you an SMS when each of these people have `
```
Go to step 7.

# Step 7 (Notify contacts)
Attempt to ring contacts. 

### Response 
File: TBC

#### If pick up
Save as notified
Send an SMS to notify that this number has been notified.

#### If do not pick up
Go to step 7 at next time interval
