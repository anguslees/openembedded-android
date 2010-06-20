DESCRIPTION = "Android SDK tools"
SECTION = "libs"
LICENSE = "Apache"
PV = "r06"

DEPENDS = "sed-native"
RDEPENDS = "virtual/java-native"
inherit java
inherit sdk

INHIBIT_DEFAULT_DEPS = "1"

SRC_URI = "http://dl.google.com/android/repository/tools_${PV}-${BUILD_OS}.zip;name=tools"
SRC_URI[tools.md5sum] = "3d196f3b21027aade41da90cbbdf2f07"
SRC_URI[tools.sha256sum] = "89d12faee15ada258f4c7419a1d6ed68dc6357fa6584c8ec66595d7c92de7415"

S = "${WORKDIR}/tools_${PV}-${BUILD_OS}"

# disk images are big, so make the emulator optional
PACKAGES =+ "android-emulator"
RDEPENDS_android-emulator += "android-emulator-data"

FILES_${PN} += "${bindir}/* ${datadir}/*"
FILES_android-emulator += "${bindir}/emulator"

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
