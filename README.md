# XChem Filedrop

Simple file drop based application that allows procesing of file.
The expectation is to read files from box.com (or similar), process them and write
results files back to box.com.

Processing currently uses a RDKIt based python script to match the structures against
a set of reaction smarts definitions and write output files for each reaction containing 
the input structures that match.

## Instructions for testing

1. Use the installDist task to build a full distribution which will be found under 
build/install.

1. create an application.properties file in directory where you run the app (e.g. the
root dir of this project - application.properties is gitignored. In that file define these
properties:
poll_interval=<default poll period in ms e.g. 5000)
box_client_id=<box.com OAth client id 
box_client_secret=<box.com client secret>
box_username=<box.com username>
box_userpassword=RAW(<box.com username>)
filter_cmd=<path to python script>/rxn_smarts_filter.py?useStderrOnEmptyStdout=true&args=-if sdf -o output

1. Run the app using build/install/xchem_filedrop/bin/xchem_filedrop. Ctrl-C to stop.

1. For testing create an input and output directory in the project root directory and drop an SDF
into the input dir.

## Instructions for running using a distribution

Similar to above, but:

1. Use a distribution tasks such as distZip to build a distribution.
1. Unzip where you plan to use it
1. Create the application.properties as described above
1. Run the app using <unzipped distro>/bin/xchem_filedrop. Ctrl-C to stop.

## Instructions for running using a Docker image

1. Build the Docker image using the dockerBuildImage gradle task
1. Create the application.properties file in the directory where you are going to run the container 
1. Run using something like this: `docker run -it --rm -v $(PWD)/application.properties:/xchem_filedrop/application.properties -w /xchem_filedrop informaticsmatters/xchem_filedrop`
