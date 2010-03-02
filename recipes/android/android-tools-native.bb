DESCRIPTION = "Android SDK tools"
SECTION = "libs"
LICENSE = "Apache"
PV = "r04"

DEPENDS = "sed-native"
RDEPENDS = "virtual/java-native"
inherit native

INHIBIT_DEFAULT_DEPS = "1"

SRC_URI = "http://dl.google.com/android/repository/tools_${PV}-${BUILD_OS}.zip"

S = "${WORKDIR}/tools_${PV}-${BUILD_OS}"

FILES_${PN} = "${bindir} ${libdir}"

do_compile() {
	:
}

bins = "adb dmtracedump emulator gprov-conv mksdcard sqlite3 zipalign"
java = "android apkbuilder ddms draw9patch hierarchyviewer layoutopt traceview"

do_stage() {
	for p in ${bins}; do
		cp -pf "${S}"/$p ${STAGING_BINDIR_NATIVE}
	done
	for p in ${java}; do
		sed -e 's,^libdir=,libdir=${STAGING_DIR}/java,' "${S}"/tools/$p >${STAGING_BINDIR_NATIVE}/$p
		chmod +x ${STAGING_BINDIR_NATIVE}/$p
	done

	cp -R -pf "${S}"/lib/* ${STAGING_DIR_JAVA}
}
