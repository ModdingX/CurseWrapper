#!/usr/bin/env python3

import os.path
import json
import subprocess

config = {}
if os.path.isfile('config.json'):
    with open('config.json', mode='r') as file:
        config = json.loads(file.read())

args = ['java', '-p', 'server.jar', '-p', 'dependencies']
args += ['-m', 'io.github.noeppi_noeppi.tools.cursewrapper.server']

args += ['--docker']

if 'no-ssl' in config and bool(config['no-ssl']):
    args += ['--no-ssl']

if 'port' in config:
    args += ['--port', str(config['port'])]

if 'threads' in config:
    args += ['--threads', str(config['threads'])]

subprocess.call(args)
