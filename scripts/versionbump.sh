#!/bin/bash

latestVersion=$1;
buildTag=$2; #required for non-prod build
if [ -z "$latestVersion" ]
then
  #Handle exception here..
  echo "Latest tag does not exists"
  exit 1
fi
echo "Latest Tag: ${latestVersion}"
#Using Internal Field Seperator to split prefix and version
IFS=' - '
read -ra logic <<< "$latestVersion"
#set prefix
preFix="${logic[0]}"
if [ "$preFix" != 'RFM' ]
then
  preFix='RFM'
fi
#set version to the variable
versionId="${logic[${#logic[@]}-1]}"
#get commitId, To get only hash value of commit
commitId=$(git log -n 1 --pretty=format:"%H")
#get current year to set major version
currentYear=$(date +%y)
#get current quarter to set minor version
currentQuarter=$(( ($(date +%-m)-1)/3+1 ))

# set versionId
if [ -z "$versionId" ]
then
  #Handle exception here..
  echo "Latest version does not exists"
  exit 1
else
  IFS='.' read -r -a array <<< "$versionId"
  versionYear="${array[0]}"
  versionQuarter="${array[1]}"
  major="${array[2]}"
  majorIncrement=$((major+1))
  minor=0
  patch=0
  if [ "$currentYear" == "$versionYear" ] && [ "$currentQuarter" == "$versionQuarter" ]
  then
     releaseVersion="$preFix"-"$versionYear"."$versionQuarter"."$majorIncrement"."$minor"."$patch"
  elif [ "$currentYear" == "$versionYear" ] && [ "$currentQuarter" != "$versionQuarter" ]
  then
     releaseVersion="$preFix"-"$versionYear"."$currentQuarter"."$majorIncrement"."$minor"."$patch"
  else
     majorReset=1
     releaseVersion="$preFix"-"$currentYear"."$currentQuarter"."$majorReset"."$minor"."$patch"
  fi
fi

#set release Version tag
if [ "$buildTag" == 'SNAPSHOT' ]
then
  releaseVersion="$releaseVersion"-"$buildTag"-"$commitId"
  echo "Bumped Tag: $releaseVersion"
  echo "::set-output name=VERSION_TAG::$releaseVersion"
else
  echo "Bumped Tag: $releaseVersion"
  echo "::set-output name=VERSION_TAG::$releaseVersion"
fi