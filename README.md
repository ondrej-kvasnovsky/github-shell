# github-shell

`github-shell` is a command-line tool that allows a user to interact with a Github organization.

## Features

Use `$ help` command to get list of available commands.

### Authenticate

You need to authenticate in order to be able to call GitHub REST API as many times as possible. 
There is a upper limit 5000 calls per our, see [rate limits](https://developer.github.com/v3/#rate-limiting) for more information.

```
$ login <username> <password>
```

### Find top N repositories by number of stars

Finds and prints out N repositories with most stars.

```
$ top-repos <number of top repositories> stars
```

For example, this prints 5 top repositories with most stars. 

```
$ top-repos 5 stars
┌────────────────────────┬─────┬─────┐
│Repository Name         │Stars│Forks│
├────────────────────────┼─────┼─────┤
│github/gitignore        │59733│26711│
├────────────────────────┼─────┼─────┤
│github/fetch            │17326│1405 │
├────────────────────────┼─────┼─────┤
│github/hub              │12000│1150 │
├────────────────────────┼─────┼─────┤
│github/linguist         │5370 │2059 │
├────────────────────────┼─────┼─────┤
│github/swift-style-guide│4425 │486  │
└────────────────────────┴─────┴─────┘
```

### Find top N repositories by number of forks

Finds and prints out N repositories with most forks.

```
$ top-repos 5 forks
```

### Find top N repositories by number of pullRequests

Finds and prints out N repositories with most pull requests.

```
$ top-repos 5 pullRequests
```

### Find top N repositories by number of contribution

Finds and prints out N repositories with contribution percentage. Contribution percentage is calculated as
number of pull requests divided by number of forks.

```
$ top-repos 5 contribution
```

Here is an example output for top contribution percentage.

```
┌─────────────────────────┬─────┬─────┬─────────┬──────────────┐
│Repository Name          │Stars│Forks│Nr of PRs│Contribution %│
├─────────────────────────┼─────┼─────┼─────────┼──────────────┤
│github/elastomer-client  │99   │16   │150      │937.50        │
├─────────────────────────┼─────┼─────┼─────────┼──────────────┤
│github/dmca              │1512 │339  │2720     │802.36        │
├─────────────────────────┼─────┼─────┼─────────┼──────────────┤
│github/s3gof3r           │4    │1    │6        │600.00        │
├─────────────────────────┼─────┼─────┼─────────┼──────────────┤
│github/chatops-controller│18   │5    │29       │580.00        │
├─────────────────────────┼─────┼─────┼─────────┼──────────────┤
│github/hubstep           │8    │8    │34       │425.00        │
└─────────────────────────┴─────┴─────┴─────────┴──────────────┘
```

## How to build and run

Java 8 is required. 

First, go into project root and build project and run tests. 

```
$ ./gradlew clean build
```

Then you can run the shell in command line. 

```
sh github-shell
```

or

```
$ java -jar ./build/libs/github-shell-0.0.1.jar
```

### Used APIs and technologies

* GitHub [REST API](https://developer.github.com/v3/search/#search-repositories)
* Spring [Shell 2](https://github.com/spring-projects/spring-shell)
* Spring [Boot](https://github.com/spring-projects/spring-boot)
* [Feign](https://github.com/OpenFeign/feign) as REST client
