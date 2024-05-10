
File deployDirectory = new File(basedir, "target/repository/de/eitco/bom/test/cicd-bom-test-bom")

assert deployDirectory.isDirectory()

File createdFile = new File(basedir, "target/bom.xml");

def project = new XmlSlurper().parse(createdFile)

assert project.groupId.text() == 'de.eitco.bom.test'
assert project.dependencyManagement != null
assert project.dependencyManagement.dependencies != null
assert project.dependencyManagement.dependencies.dependency[0].artifactId.text() == 'cicd-bom-test'
assert project.dependencyManagement.dependencies.dependency[1].artifactId.text() == 'cicd-bom-test-first-first'
assert project.dependencyManagement.dependencies.dependency[7].artifactId.text() == 'wagon-maven-plugin'
