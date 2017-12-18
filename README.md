# introsde-2017-assignment-3-client

Federico Baldessari | federico.baldessari@studenti.unitn.it

## Project 
The Client is divided in 3 packages:
- `introsde.assignment3.client`: the Client that performs all the requests and evaluation
- `introsde.assignment3.soap`: the generated classes from WSDL `wsimport -keep <ORIGIN>/people?wsdl`
- `introsde.assignment3.utils`: contains `PeopleGenerator` that allow to easily create a new random person or activity with all the fields compiled. `PrettyPrinter` to manage the formatting and writing into file of requests and response.

To evaluate the correctness of the server the client performs:
- #0 **Create** 3 new random person and activities if database is empty
- #1 **readPersonList**: List all people in database
- #2 **readPerson**: Read first person
- #3 **updatePerson** : Change firstname of first person
  - #2 *readPerson*: Read again first person
- #4 **createPerson**: Create new person
  - #2 *readPerson*: Read person created
- #5 **deletePerson**: Delete created person
  - #2 *readPerson*: Read person created (expected null)
- #6 **readPersonPreferences**: Read all activity with activityType='Sport' for first Person
- #7 **readPreferences**: Read all activities
- #9 **savePersonPreference**: Create new activity with activityType='Sport' for fist Person
- #8 **readPersonPreference**: Read new activity created
- #10 **updatePersonPreferences**: Update new activity created with random name
- #11 **evalutatePersonPreferences**: Evaluate new activity created with random rate
  - #8 *readPersonPreference*: Read again new activity to evaluate #10 and #11
- #12 **getBestPersonPreference**: Get best preference of first person ordered by rate desc
  - #1 *readPersonList*: List all people in database: To evaluate again every step made

## Execution

Clone the repository and enter the directory
```
git clone https://github.com/balde73/introsde-2017-assignment-3-client.git && cd introsde-2017-assignment-3-client
```
Use ant to execute client. By default will use the server deployed on heroku
```
ant execute.client
```
If you want to evaluate the server on localhost change the java class `introsde.assignment3.client.Client` [here](https://github.com/balde73/introsde-2017-assignment-3-client/blob/fa3fc505d3ec4db4429233e8daedc01cf25e3674/src/introsde/assignment3/client/Client.java#L17) with the correct address (probably http://localhost:6902/people?wsdl).

## Additional Notes

The generated classes are already in the repository just to decrease the steps of deployment.
