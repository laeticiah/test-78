*
 * Configure the Jenkins EC2 Plugin via Groovy Script
 * EC2 Plugin URL: https://wiki.jenkins-ci.org/display/JENKINS/Amazon+EC2+Plugin
 */

import hudson.model.*
import jenkins.model.*
import hudson.plugins.ec2.*
import com.amazonaws.services.ec2.model.InstanceType
  
def instance = Jenkins.getInstance()

def ec2_cloud_name = 'gist-example-cloud'
def ec2_instance_cap = 5

def worker_description = 'jenkins-worker'
def worker_label_string = 'worker'

def ami_id = 'ami-AAAAAAAA'
def security_groups = 'sg-11111111,sg-22222222'
def subnet_id = 'subnet-SSSSSSSS'
def instance_type = 'm3.2xlarge'
def instance_profile_arn = 'arn:aws:iam::123456789012:instance-profile/JenkinsInstanceProfile'

def number_of_executors = 8

def ec2_tags = [
  new EC2Tag('Name', 'jenkins-worker')
]

def priv_key_txt = '''
-----BEGIN RSA PRIVATE KEY-----
XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
-----END RSA PRIVATE KEY-----
'''

  

def worker_ami = new SlaveTemplate(
  // String ami
  ami_id,
  // String zone
  '',
  // SpotConfiguration spotConfig
  null,
  // String securityGroups
  security_groups,
  // String remoteFS
  '',
  // InstanceType type
  InstanceType.fromValue(instance_type),
  // boolean ebsOptimized
  false,
  // String labelString
  worker_label_string,
  // Node.Mode mode
  Node.Mode.NORMAL,
  // String description
  worker_description,
  // String initScript
  '',
  // String tmpDir
  '',
  // String userData
  '',
  // String numExecutors
  "${number_of_executors}",
  // String remoteAdmin
  '',
  // AMITypeData amiType
  new UnixData(null, null),
  // String jvmopts
  '',
  // boolean stopOnTerminate
  false,
  // String subnetId
  subnet_id,
  // List<EC2Tag> tags
  ec2_tags,
  // String idleTerminationMinutes
  '30',
  // boolean usePrivateDnsName
  true,
  // String instanceCapStr
  '50',
  // String iamInstanceProfile
  instance_profile_arn,
  // boolean useEphemeralDevices
  true,
  // boolean useDedicatedTenancy
  false,
  // String launchTimeoutStr
  '1800',
  // boolean associatePublicIp
  false,
  // String customDeviceMapping
  '',
  // boolean connectBySSHProcess
  false,
  // boolean connectUsingPublicIp
  false
)

def new_cloud = new AmazonEC2Cloud(
  // String cloudName
  ec2_cloud_name,
  // boolean useInstanceProfileForCredentials
  true,
  // String credentialsId
  '',
  // String region
  'us-east-1',
  // String privateKey
  priv_key_txt,
  // String instanceCapStr
  "${ec2_instance_cap}",
  // List<? extends SlaveTemplate> templates
  [worker_ami]
)

instance.clouds.add(new_cloud)
