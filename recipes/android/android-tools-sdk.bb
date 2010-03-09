DESCRIPTION = "Android SDK tools"
SECTION = "libs"
LICENSE = "Apache"
PV = "r04"

DEPENDS = "sed-native"
RDEPENDS = "virtual/java-native"
inherit java
inherit sdk

INHIBIT_DEFAULT_DEPS = "1"

SRC_URI = "http://dl.google.com/android/repository/tools_${PV}-${BUILD_OS}.zip"

S = "${WORKDIR}/tools_${PV}-${BUILD_OS}"

FILES_${PN} += "${bindir}/* ${base_bindir}/* ${bindir}/* \
  ${datadir}/java ${datadir}/java"

do_compile() {
	:
}

bins = "adb dmtracedump emulator hprof-conv mksdcard sqlite3 zipalign"
java = "android apkbuilder ddms draw9patch hierarchyviewer layoutopt traceview"

do_stage() {
	install -d ${STAGING_BINDIR_NATIVE}
	for p in ${bins}; do
		cp -pf "${S}"/$p ${STAGING_BINDIR_NATIVE}
	done

	for p in ${java}; do
		sed -e 's,^libdir=.*,libdir=${STAGING_DATADIR_JAVA_NATIVE},' "${S}"/$p >${STAGING_BINDIR_NATIVE}/$p
		chmod +x ${STAGING_BINDIR_NATIVE}/$p
	done

	install -d ${STAGING_DATADIR_JAVA_NATIVE}
	cp -R -pf "${S}"/lib/* ${STAGING_DATADIR_JAVA_NATIVE}
}

do_install() {
	install -d ${D}${bindir_native}
	for p in ${bins}; do
		install -m 0755 ${S}/$p ${D}${bindir_native}
	done

	for p in ${java}; do
		sed -e 's,^libdir=.*,libdir=${datadir_native}/java,' "${S}"/$p >${D}${bindir_native}/$p
		chmod +x ${D}${bindir_native}/$p
	done

	install -d ${D}${datadir_native}/java
	cp -R -pf "${S}"/lib/* ${D}${datadir_native}/java
}
