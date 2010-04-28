DESCRIPTION = "Android SDK tools"
SECTION = "libs"
LICENSE = "Apache"
PV = "r05"

DEPENDS = "sed-native"
RDEPENDS = "virtual/java-native"
inherit java
inherit sdk

INHIBIT_DEFAULT_DEPS = "1"

SRC_URI = "http://dl.google.com/android/repository/tools_${PV}-${BUILD_OS}.zip;name=tools"
SRC_URI[tools.md5sum] = "9e7df5d25237fb8cbdc08ce1d71905b3"
SRC_URI[tools.sha256sum] = "b51a9c22734d4f0daa3faae793d970872dda5eb28c618b237ef5d233e68aaa93"

S = "${WORKDIR}/tools_${PV}-${BUILD_OS}"

FILES_${PN} += "${bindir}/* ${datadir}/*"

do_compile() {
	:
}

bins = "adb dmtracedump emulator etc1tool hprof-conv mksdcard sqlite3 zipalign"
java = "android apkbuilder ddms draw9patch hierarchyviewer layoutopt traceview"

do_install() {
	install -d ${D}${bindir}
	for p in ${bins}; do
		install -m 0755 ${S}/$p ${D}${bindir}
	done

	for p in ${java}; do
		sed -e 's,^frameworkdir=.*,frameworkdir=${datadir}/java,' "${S}"/$p >${D}${bindir}/$p
		chmod +x ${D}${bindir}/$p
	done

	install -d ${D}${datadir}/java
	cp -R -pf "${S}"/lib/* ${D}${datadir}/java
	chmod a+rX -R ${D}${datadir}/java
	install -d ${D}${datadir}/emacs/site-lisp
	mv ${D}${datadir}/java/android.el ${D}${datadir}/emacs/site-lisp/
}
