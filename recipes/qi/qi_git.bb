require qi.inc
PR = "r3"
PR_append = "+gitr${SRCPV}"

SRCREV = "c38b062a609f1442e6a9e13005cfbdfd59a5ac0d"
SRC_URI = "\
  git://git.openmoko.org/git/qi.git;protocol=git;branch=master \
  file://sanitize-makefile.patch \
  file://0001-use-rootwait-instead-of-rootdelay.patch \
"
S = "${WORKDIR}/git"

