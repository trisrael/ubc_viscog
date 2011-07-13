require "buildr/openjpa"

include Buildr::OpenJPA
 
VERSION_NUMBER = '1.0'

layout = Layout.new
layout[:source, :main, :java] = 'src'
layout[:target, :main] = 'bin'

repositories.remote << "http://repo2.maven.org/maven2/"

define 'corr_prog' do
  project.version = VERSION_NUMBER
  compile.with 'org.swinglabs:swing-worker:jar:1.1'
  package :jar
end