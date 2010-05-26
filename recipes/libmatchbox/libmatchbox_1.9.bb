require libmatchbox.inc
PR = "r6"

SRC_URI = "\
  http://projects.o-hand.com/matchbox/sources/${PN}/${PV}/${PN}-${PV}.tar.gz \
  file://16bppfixes.patch \
  file://configure_fixes.patch \
  file://reset-sigchld.patch \
  file://check.m4\
"

do_configure_prepend () {
	cp ${WORKDIR}/check.m4 ${S}/
}

SRC_URI[md5sum] = "465fa15c43bf0091a3810e7702fe143f"
SRC_URI[sha256sum] = "f7054f93c57ba6b758d0e4f47d4d2dd96a7fe487e1157eb70a4d642910275aea"
