DESCRIPTION = "Android SDK tools"
SECTION = "libs"
LICENSE = "Apache"
PV = "r04"

DEPENDS = "sed-native"
RDEPENDS = "virtual/java-native"
inherit java
inherit sdk

INHIBIT_DEFAULT_DEPS = "1"

SRC_URI = "http://dl.google.com/android/repository/tools_${PV}-${BUILD_OS}.zip;name=tools"
SRC_URI[tools.md5sum] = "e2912dbbed6c1e9cb16550cb875ad42d"
SRC_URI[tools.sha256sum] = "bc0c6c198405445e8a6e8520298470ddb298e69867bb5f066b6493ab1e35e326"

S = "${WORKDIR}/tools_${PV}-${BUILD_OS}"

FILES_${PN} += "${bindir}/* ${datadir}/*"

do_compile() {
	:
}

bins = "adb dmtracedump emulator hprof-conv mksdcard sqlite3 zipalign"
java = "android apkbuilder ddms draw9patch hierarchyviewer layoutopt traceview"

do_install() {
	install -d ${D}${bindir}
	for p in ${bins}; do
		install -m 0755 ${S}/$p ${D}${bindir}
	done

	for p in ${java}; do
		sed -e 's,^libdir=.*,libdir=${datadir}/java,' "${S}"/$p >${D}${bindir}/$p
		chmod +x ${D}${bindir}/$p
	done

	install -d ${D}${datadir}/java
	cp -R -pf "${S}"/lib/* ${D}${datadir}/java
	chmod a+rX -R ${D}${datadir}/java
	install -d ${D}${datadir}/emacs/site-lisp
	mv ${D}${datadir}/java/android.el ${D}${datadir}/emacs/site-lisp/
}
