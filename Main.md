# Sahana for Android #
Goals and Plans

# Introduction #

We are aim to create an application to allow relief workers in the field to submit and view disaster-related incident reports to aid in response and relief. The reports would be retrieved and uploaded to the Sahana database to enable relief workers, NGOs and governments to coordinate and analyze disaster relief efforts.  Please see the features segment for a detailed breakdown of the types of activities that this application will perform.

Our goal is to provide a means for relief workers to report incidents that require disaster relief services using Android phones. Because cell phones have less power requirements, are mobile, and have access to different communication networks (data networks, satellite, etc), they are more accessible and practical for relief workers to use on the field as a tool for disaster management efforts. Currently, there is no mobile application to help gather data about regions where relief workers are currently deployed or keep them up to date about incidents that might have occurred.  We aim to integrate our application with Sahana, an open-source, web-based disaster management service, with a python back-end and javascript  web interface. Sahana has already been deployed to manage disasters in Sri Lanka, Katrina, Pakistan, etc and has a strong user base.

Our primary customers/clients are as follows:
**First-responders:**  Community leaders, medical specialists, and municipal police and fire service that arrive on the scene of a disaster have special skills that they can deploy.  However, in a large-scale disaster environment, the demand for their services may outstrip the supply.  In cases like this it is invaluable for good lines of communication to exist between first responders and those who can send more resources to the site of a disaster.  Hospitals and municipal services can use the data they receive from incident reports to direct life-saving aid to the areas that need the most help.
**NGOs:** Non-governmental organizations are exceptionally important in responding to disasters as they often possess expertise far beyond the local government.  They can use this experience to organize volunteers and workers to quickly reach and properly care for those affected by disaster.  NGOs can use incident reports to determine where to organize medical aid, shelter, and food relief for disaster victims.
**Governments:** Local and regional governments most often have the greatest available resources to quickly respond to a disaster in their area on a large scale.  Governments can use incident reports to assess and dispatch aid to areas that need immediate relief..

# Details #

Priority 1
> Handset Database Storage - Ability to store incident reports in a local (offline) SQLite database.  The ability to access reports and submit reports while not connected to a network is very important in the field when network connectivity may not be available.  Reports then can persist from session to session without having to query a remote source each time the application starts.  Finally, the database can synchronize reports with the server at a time when there is a network connection.
> Incident Report input generation - Enabling the user to fill out a complete report and save it to the handset.
> Attach photos to reports - This feature would allow the user to add one, or many photos to their report.  The photos would appear in thumbnail form in the form as the user is adding them.  The photos will be taken from either an internal image source or directly from the camera activity, then added to the form.
> Incident Listing (listview) - This would be a view that allows the user to list the reports stored in the database and to then examine and edit individual reports.
> Individual incident summary page - This would be a view of an individual report after a user has selected a report to view.  Some of the the fields would then be editable so that users can change their locations if the GPS did not properly record, or add or delete photos.

Priority 2
> GPS Location sensing - A GPS service will provide the current user location to the form so that each form submission is tagged with the current location.
> Stored photo viewing - This feature would allow the user, when looking at individual reports, to view a larger version of photos attached to the report.
> Save/Load menu default parameters - Allow the user to define default settings from the menu screen.  This would include parameters such as the reporter’s name, preferred start screen, remote server address, and log-in information. Alternatively, the users can have a log-in screen on application start up, where they can choose to save their log-in information.
> Remote Server Submission - This is an important feature that will enable the app to submit incident reports to the Sahana server.  This will involve sending the result through an HTTP request to the Sahana API.  Having all the data on a centralized server allows admins to coordinate response efforts over a wide range of incident and other data, sourced from a large number of people in the field.
> Incident Mapping - This feature would enable each incident to be mapped to a Google Map.  Since each incident is inherently tied to a location, this feature can be highly valuable in getting a better visual idea of where incidents are taking place.  This feature can be used for displaying nearby incidents, for displaying individual incident locations, or for helping the user define or edit the location of an incident that is being reported.

Priority 3
> Start report from map activity - This would extend the capabilities of our map allowing users to click on nearby incidents and be taken to the individual incident reports.
> Remote incident loading - This feature would compliment the ability to submit forms via the network to the Sahana server.  Remote incident reports would be loaded via the Sahana API to a local database and presented in the incident list.  This will allow users to view recent or nearby reports that were submitted by other users, granting a larger sphere of information to each user in the field.

Priority 4
> List filtering - The ability to dynamically filter, search,  and sort the incident list based on such parameters such as time, nearby incidents, category, number of photos, or other incident parameters.  This would enable users to quickly sift through large number of incidents.
> Incident Administration options (Auth-based) - Administrative users on the Sahana server have access to additional information attached to incident reports.  By allowing these users to access the same information in the field, their workflow can take them further away from a computer terminal.
> XForms support - XForms provides a schema to the fields of submission forms.  Using this schema, the application could dynamically generate the correct fields required to process the input.  This feature could allow the incident reporter to collect different types of data, beyond incidents, as well as streamline the UI development and data validation processes.