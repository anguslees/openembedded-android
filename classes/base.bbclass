BB_DEFAULT_TASK ?= "build"

# like os.path.join but doesn't treat absolute RHS specially
def base_path_join(a, *p):
    path = a
    for b in p:
        if path == '' or path.endswith('/'):
            path +=  b
        else:
            path += '/' + b
    return path

def base_path_relative(src, dest):
    """ Return a relative path from src to dest.

    >>> base_path_relative("/usr/bin", "/tmp/foo/bar")
    ../../tmp/foo/bar

    >>> base_path_relative("/usr/bin", "/usr/lib")
    ../lib

    >>> base_path_relative("/tmp", "/tmp/foo/bar")
    foo/bar
    """
    from os.path import sep, pardir, normpath, commonprefix

    destlist = normpath(dest).split(sep)
    srclist = normpath(src).split(sep)

    # Find common section of the path
    common = commonprefix([destlist, srclist])
    commonlen = len(common)

    # Climb back to the point where they differentiate
    relpath = [ pardir ] * (len(srclist) - commonlen)
    if commonlen < len(destlist):
        # Add remaining portion
        relpath += destlist[commonlen:]

    return sep.join(relpath)

def base_path_out(path, d):
    """ Prepare a path for display to the user. """
    rel = base_path_relative(d.getVar("TOPDIR", 1), path)
    if len(rel) > len(path):
        return path
    else:
        return rel

# for MD5/SHA handling
def base_chk_load_parser(config_paths):
    import ConfigParser
    parser = ConfigParser.ConfigParser()
    if len(parser.read(config_paths)) < 1:
        raise ValueError("no ini files could be found")

    return parser

def base_chk_file_vars(parser, localpath, params, data):
    try:
        name = params["name"]
    except KeyError:
        return False
    flagName = "%s.md5sum" % name
    want_md5sum = bb.data.getVarFlag("SRC_URI", flagName, data)
    flagName = "%s.sha256sum" % name
    want_sha256sum = bb.data.getVarFlag("SRC_URI", flagName, data)

    if (want_sha256sum == None and want_md5sum == None):
        # no checksums to check, nothing to do
        return False

    if not os.path.exists(localpath):
        localpath = base_path_out(localpath, data)
        bb.note("The localpath does not exist '%s'" % localpath)
        raise Exception("The path does not exist '%s'" % localpath)

    if want_md5sum:
        try:
	    md5pipe = os.popen('PATH=%s md5sum %s' % (bb.data.getVar('PATH', data, True), localpath))
            md5data = (md5pipe.readline().split() or [ "" ])[0]
            md5pipe.close()
        except OSError, e:
            raise Exception("Executing md5sum failed")
        if want_md5sum != md5data:
            bb.note("The MD5Sums did not match. Wanted: '%s' and Got: '%s'" % (want_md5sum, md5data))
            raise Exception("MD5 Sums do not match. Wanted: '%s' Got: '%s'" % (want_md5sum, md5data))

    if want_sha256sum:
        try:
            shapipe = os.popen('PATH=%s oe_sha256sum %s' % (bb.data.getVar('PATH', data, True), localpath))
            sha256data = (shapipe.readline().split() or [ "" ])[0]
            shapipe.close()
        except OSError, e:
            raise Exception("Executing shasum failed")
        if want_sha256sum != sha256data:
            bb.note("The SHA256Sums did not match. Wanted: '%s' and Got: '%s'" % (want_sha256sum, sha256data))
            raise Exception("SHA256 Sums do not match. Wanted: '%s' Got: '%s'" % (want_sha256sum, sha256data))

    return True


def base_chk_file(parser, pn, pv, src_uri, localpath, data):
    no_checksum = False
    # Try PN-PV-SRC_URI first and then try PN-SRC_URI
    # we rely on the get method to create errors
    pn_pv_src = "%s-%s-%s" % (pn,pv,src_uri)
    pn_src    = "%s-%s" % (pn,src_uri)
    if parser.has_section(pn_pv_src):
        md5    = parser.get(pn_pv_src, "md5")
        sha256 = parser.get(pn_pv_src, "sha256")
    elif parser.has_section(pn_src):
        md5    = parser.get(pn_src, "md5")
        sha256 = parser.get(pn_src, "sha256")
    elif parser.has_section(src_uri):
        md5    = parser.get(src_uri, "md5")
        sha256 = parser.get(src_uri, "sha256")
    else:
        no_checksum = True

    # md5 and sha256 should be valid now
    if not os.path.exists(localpath):
        localpath = base_path_out(localpath, data)
        bb.note("The localpath does not exist '%s'" % localpath)
        raise Exception("The path does not exist '%s'" % localpath)


    # call md5(sum) and shasum
    try:
	md5pipe = os.popen('PATH=%s md5sum %s' % (bb.data.getVar('PATH', data, True), localpath))
        md5data = (md5pipe.readline().split() or [ "" ])[0]
        md5pipe.close()
    except OSError:
        raise Exception("Executing md5sum failed")

    try:
        shapipe = os.popen('PATH=%s oe_sha256sum %s' % (bb.data.getVar('PATH', data, True), localpath))
        shadata = (shapipe.readline().split() or [ "" ])[0]
        shapipe.close()
    except OSError:
        raise Exception("Executing shasum failed")

    if no_checksum == True:	# we do not have conf/checksums.ini entry
        try:
            file = open("%s/checksums.ini" % bb.data.getVar("TMPDIR", data, 1), "a")
        except:
            return False

        if not file:
            raise Exception("Creating checksums.ini failed")
        
        file.write("[%s]\nmd5=%s\nsha256=%s\n\n" % (src_uri, md5data, shadata))
        file.close()

        from string import maketrans
        trtable = maketrans("", "")
        uname = src_uri.split("/")[-1].translate(trtable, "-+._")

        try:
            ufile = open("%s/%s.sum" % (bb.data.getVar("TMPDIR", data, 1), uname), "wt")
        except:
            return False

        if not ufile:
            raise Exception("Creating %s.sum failed" % uname)

        ufile.write("SRC_URI = \"%s;name=%s\"\nSRC_URI[%s.md5sum] = \"%s\"\nSRC_URI[%s.sha256sum] = \"%s\"\n" % (src_uri, uname, uname, md5data, uname, shadata))
        ufile.close()

        if not bb.data.getVar("OE_STRICT_CHECKSUMS",data, True):
            bb.note("This package has no entry in checksums.ini, please add one")
            bb.note("\n[%s]\nmd5=%s\nsha256=%s" % (src_uri, md5data, shadata))
            bb.note("This package has no checksums in corresponding recipe, please add")
            bb.note("SRC_URI = \"%s;name=%s\"\nSRC_URI[%s.md5sum] = \"%s\"\nSRC_URI[%s.sha256sum] = \"%s\"\n" % (src_uri, uname, uname, md5data, uname, shadata))
            return True
        else:
            bb.note("Missing checksum")
            return False

    if not md5 == md5data:
        bb.note("The MD5Sums did not match. Wanted: '%s' and Got: '%s'" % (md5,md5data))
        raise Exception("MD5 Sums do not match. Wanted: '%s' Got: '%s'" % (md5, md5data))

    if not sha256 == shadata:
        bb.note("The SHA256 Sums do not match. Wanted: '%s' Got: '%s'" % (sha256,shadata))
        raise Exception("SHA256 Sums do not match. Wanted: '%s' Got: '%s'" % (sha256, shadata))

    return True


def base_dep_prepend(d):
	#
	# Ideally this will check a flag so we will operate properly in
	# the case where host == build == target, for now we don't work in
	# that case though.
	#
	deps = "shasum-native coreutils-native"
	if bb.data.getVar('PN', d, True) == "shasum-native" or bb.data.getVar('PN', d, True) == "stagemanager-native":
		deps = ""
	if bb.data.getVar('PN', d, True) == "coreutils-native":
		deps = "shasum-native"

	# INHIBIT_DEFAULT_DEPS doesn't apply to the patch command.  Whether or  not
	# we need that built is the responsibility of the patch function / class, not
	# the application.
	if not bb.data.getVar('INHIBIT_DEFAULT_DEPS', d):
		if (bb.data.getVar('HOST_SYS', d, 1) !=
	     	    bb.data.getVar('BUILD_SYS', d, 1)):
			deps += " virtual/${TARGET_PREFIX}gcc virtual/libc "
	return deps

def base_read_file(filename):
	try:
		f = file( filename, "r" )
	except IOError, reason:
		return "" # WARNING: can't raise an error now because of the new RDEPENDS handling. This is a bit ugly. :M:
	else:
		return f.read().strip()
	return None

def base_ifelse(condition, iftrue = True, iffalse = False):
    if condition:
        return iftrue
    else:
        return iffalse

def base_conditional(variable, checkvalue, truevalue, falsevalue, d):
	if bb.data.getVar(variable,d,1) == checkvalue:
		return truevalue
	else:
		return falsevalue

def base_less_or_equal(variable, checkvalue, truevalue, falsevalue, d):
	if float(bb.data.getVar(variable,d,1)) <= float(checkvalue):
		return truevalue
	else:
		return falsevalue

def base_version_less_or_equal(variable, checkvalue, truevalue, falsevalue, d):
    result = bb.vercmp(bb.data.getVar(variable,d,True), checkvalue)
    if result <= 0:
        return truevalue
    else:
        return falsevalue

def base_contains(variable, checkvalues, truevalue, falsevalue, d):
	val = bb.data.getVar(variable,d,1)
	if not val:
		return falsevalue
	matches = 0
	if type(checkvalues).__name__ == "str":
		checkvalues = [checkvalues]
	for value in checkvalues:
		if val.find(value) != -1:
			matches = matches + 1
	if matches == len(checkvalues):
		return truevalue
	return falsevalue

def base_both_contain(variable1, variable2, checkvalue, d):
       if bb.data.getVar(variable1,d,1).find(checkvalue) != -1 and bb.data.getVar(variable2,d,1).find(checkvalue) != -1:
               return checkvalue
       else:
               return ""

DEPENDS_prepend="${@base_dep_prepend(d)} "
DEPENDS_virtclass-native_prepend="${@base_dep_prepend(d)} "
DEPENDS_virtclass-nativesdk_prepend="${@base_dep_prepend(d)} "

def base_prune_suffix(var, suffixes, d):
    # See if var ends with any of the suffixes listed and 
    # remove it if found
    for suffix in suffixes:
        if var.endswith(suffix):
            return var.replace(suffix, "")
    return var

def base_set_filespath(path, d):
	bb.note("base_set_filespath usage is deprecated, %s should be fixed" % d.getVar("P", 1))
	filespath = []
	# The ":" ensures we have an 'empty' override
	overrides = (bb.data.getVar("OVERRIDES", d, 1) or "") + ":"
	for p in path:
		for o in overrides.split(":"):
			filespath.append(os.path.join(p, o))
	return ":".join(filespath)

def oe_filter(f, str, d):
	from re import match
	return " ".join(filter(lambda x: match(f, x, 0), str.split()))

def oe_filter_out(f, str, d):
	from re import match
	return " ".join(filter(lambda x: not match(f, x, 0), str.split()))

die() {
	oefatal "$*"
}

oenote() {
	echo "NOTE:" "$*"
}

oewarn() {
	echo "WARNING:" "$*"
}

oefatal() {
	echo "FATAL:" "$*"
	exit 1
}

oedebug() {
	test $# -ge 2 || {
		echo "Usage: oedebug level \"message\""
		exit 1
	}

	test ${OEDEBUG:-0} -ge $1 && {
		shift
		echo "DEBUG:" $*
	}
}

oe_runmake() {
	if [ x"$MAKE" = x ]; then MAKE=make; fi
	oenote ${MAKE} ${EXTRA_OEMAKE} "$@"
	${MAKE} ${EXTRA_OEMAKE} "$@" || die "oe_runmake failed"
}

oe_soinstall() {
	# Purpose: Install shared library file and
	#          create the necessary links
	# Example:
	#
	# oe_
	#
	#oenote installing shared library $1 to $2
	#
	libname=`basename $1`
	install -m 755 $1 $2/$libname
	sonamelink=`${HOST_PREFIX}readelf -d $1 |grep 'Library soname:' |sed -e 's/.*\[\(.*\)\].*/\1/'`
	solink=`echo $libname | sed -e 's/\.so\..*/.so/'`
	ln -sf $libname $2/$sonamelink
	ln -sf $libname $2/$solink
}

oe_libinstall() {
	# Purpose: Install a library, in all its forms
	# Example
	#
	# oe_libinstall libltdl ${STAGING_LIBDIR}/
	# oe_libinstall -C src/libblah libblah ${D}/${libdir}/
	dir=""
	libtool=""
	silent=""
	require_static=""
	require_shared=""
	staging_install=""
	while [ "$#" -gt 0 ]; do
		case "$1" in
		-C)
			shift
			dir="$1"
			;;
		-s)
			silent=1
			;;
		-a)
			require_static=1
			;;
		-so)
			require_shared=1
			;;
		-*)
			oefatal "oe_libinstall: unknown option: $1"
			;;
		*)
			break;
			;;
		esac
		shift
	done

	libname="$1"
	shift
	destpath="$1"
	if [ -z "$destpath" ]; then
		oefatal "oe_libinstall: no destination path specified"
	fi
	if echo "$destpath/" | egrep '^${STAGING_LIBDIR}/' >/dev/null
	then
		staging_install=1
	fi

	__runcmd () {
		if [ -z "$silent" ]; then
			echo >&2 "oe_libinstall: $*"
		fi
		$*
	}

	if [ -z "$dir" ]; then
		dir=`pwd`
	fi

	dotlai=$libname.lai

	# Sanity check that the libname.lai is unique
	number_of_files=`(cd $dir; find . -name "$dotlai") | wc -l`
	if [ $number_of_files -gt 1 ]; then
		oefatal "oe_libinstall: $dotlai is not unique in $dir"
	fi


	dir=$dir`(cd $dir;find . -name "$dotlai") | sed "s/^\.//;s/\/$dotlai\$//;q"`
	olddir=`pwd`
	__runcmd cd $dir

	lafile=$libname.la

	# If such file doesn't exist, try to cut version suffix
	if [ ! -f "$lafile" ]; then
		libname1=`echo "$libname" | sed 's/-[0-9.]*$//'`
		lafile1=$libname.la
		if [ -f "$lafile1" ]; then
			libname=$libname1
			lafile=$lafile1
		fi
	fi

	if [ -f "$lafile" ]; then
		# libtool archive
		eval `cat $lafile|grep "^library_names="`
		libtool=1
	else
		library_names="$libname.so* $libname.dll.a"
	fi

	__runcmd install -d $destpath/
	dota=$libname.a
	if [ -f "$dota" -o -n "$require_static" ]; then
		__runcmd install -m 0644 $dota $destpath/
	fi
	if [ -f "$dotlai" -a -n "$libtool" ]; then
		if test -n "$staging_install"
		then
			# stop libtool using the final directory name for libraries
			# in staging:
			__runcmd rm -f $destpath/$libname.la
			__runcmd sed -e 's/^installed=yes$/installed=no/' \
				     -e '/^dependency_libs=/s,${WORKDIR}[[:alnum:]/\._+-]*/\([[:alnum:]\._+-]*\),${STAGING_LIBDIR}/\1,g' \
				     -e "/^dependency_libs=/s,\([[:space:]']\)${libdir},\1${STAGING_LIBDIR},g" \
				     $dotlai >$destpath/$libname.la
		else
			__runcmd install -m 0644 $dotlai $destpath/$libname.la
		fi
	fi

	for name in $library_names; do
		files=`eval echo $name`
		for f in $files; do
			if [ ! -e "$f" ]; then
				if [ -n "$libtool" ]; then
					oefatal "oe_libinstall: $dir/$f not found."
				fi
			elif [ -L "$f" ]; then
				__runcmd cp -P "$f" $destpath/
			elif [ ! -L "$f" ]; then
				libfile="$f"
				__runcmd install -m 0755 $libfile $destpath/
			fi
		done
	done

	if [ -z "$libfile" ]; then
		if  [ -n "$require_shared" ]; then
			oefatal "oe_libinstall: unable to locate shared library"
		fi
	elif [ -z "$libtool" ]; then
		# special case hack for non-libtool .so.#.#.# links
		baselibfile=`basename "$libfile"`
		if (echo $baselibfile | grep -qE '^lib.*\.so\.[0-9.]*$'); then
			sonamelink=`${HOST_PREFIX}readelf -d $libfile |grep 'Library soname:' |sed -e 's/.*\[\(.*\)\].*/\1/'`
			solink=`echo $baselibfile | sed -e 's/\.so\..*/.so/'`
			if [ -n "$sonamelink" -a x"$baselibfile" != x"$sonamelink" ]; then
				__runcmd ln -sf $baselibfile $destpath/$sonamelink
			fi
			__runcmd ln -sf $baselibfile $destpath/$solink
		fi
	fi

	__runcmd cd "$olddir"
}

def package_stagefile(file, d):

    if bb.data.getVar('PSTAGING_ACTIVE', d, True) == "1":
        destfile = file.replace(bb.data.getVar("TMPDIR", d, 1), bb.data.getVar("PSTAGE_TMPDIR_STAGE", d, 1))
        bb.mkdirhier(os.path.dirname(destfile))
        #print "%s to %s" % (file, destfile)
        bb.copyfile(file, destfile)

package_stagefile_shell() {
	if [ "$PSTAGING_ACTIVE" = "1" ]; then
		srcfile=$1
		destfile=`echo $srcfile | sed s#${TMPDIR}#${PSTAGE_TMPDIR_STAGE}#`
		destdir=`dirname $destfile`
		mkdir -p $destdir
		cp -dp $srcfile $destfile
	fi
}

oe_machinstall() {
	# Purpose: Install machine dependent files, if available
	#          If not available, check if there is a default
	#          If no default, just touch the destination
	# Example:
	#                $1  $2   $3         $4
	# oe_machinstall -m 0644 fstab ${D}/etc/fstab
	#
	# TODO: Check argument number?
	#
	filename=`basename $3`
	dirname=`dirname $3`

	for o in `echo ${OVERRIDES} | tr ':' ' '`; do
		if [ -e $dirname/$o/$filename ]; then
			oenote $dirname/$o/$filename present, installing to $4
			install $1 $2 $dirname/$o/$filename $4
			return
		fi
	done
#	oenote overrides specific file NOT present, trying default=$3...
	if [ -e $3 ]; then
		oenote $3 present, installing to $4
		install $1 $2 $3 $4
	else
		oenote $3 NOT present, touching empty $4
		touch $4
	fi
}

addtask listtasks
do_listtasks[nostamp] = "1"
python do_listtasks() {
	import sys
	# emit variables and shell functions
	#bb.data.emit_env(sys.__stdout__, d)
	# emit the metadata which isnt valid shell
	for e in d.keys():
		if bb.data.getVarFlag(e, 'task', d):
			sys.__stdout__.write("%s\n" % e)
}

addtask clean
do_clean[dirs] = "${TOPDIR}"
do_clean[nostamp] = "1"
python base_do_clean() {
	"""clear the build and temp directories"""
	dir = bb.data.expand("${WORKDIR}", d)
	if dir == '//': raise bb.build.FuncFailed("wrong DATADIR")
	bb.note("removing " + base_path_out(dir, d))
	os.system('rm -rf ' + dir)

	dir = "%s.*" % bb.data.expand(bb.data.getVar('STAMP', d), d)
	bb.note("removing " + base_path_out(dir, d))
	os.system('rm -f '+ dir)
}

python do_cleanall() {
    pass
}
do_cleanall[recrdeptask] = "do_clean"
addtask cleanall after do_clean

addtask rebuild after do_${BB_DEFAULT_TASK}
do_rebuild[dirs] = "${TOPDIR}"
do_rebuild[nostamp] = "1"
python base_do_rebuild() {
	"""rebuild a package"""
}

addtask mrproper
do_mrproper[dirs] = "${TOPDIR}"
do_mrproper[nostamp] = "1"
python base_do_mrproper() {
	"""clear downloaded sources, build and temp directories"""
	dir = bb.data.expand("${DL_DIR}", d)
	if dir == '/': bb.build.FuncFailed("wrong DATADIR")
	bb.debug(2, "removing " + dir)
	os.system('rm -rf ' + dir)
	bb.build.exec_func('do_clean', d)
}

addtask distclean
do_distclean[dirs] = "${TOPDIR}"
do_distclean[nostamp] = "1"
python base_do_distclean() {
	"""clear downloaded sources, build and temp directories"""

	bb.build.exec_func('do_clean', d)

	src_uri = bb.data.getVar('SRC_URI', d, 1)
	if not src_uri:
		return

	for uri in src_uri.split():
		if bb.decodeurl(uri)[0] == "file":
			continue

		try:
			local = bb.data.expand(bb.fetch.localpath(uri, d), d)
		except bb.MalformedUrl, e:
			bb.debug(1, 'Unable to generate local path for malformed uri: %s' % e)
		else:
			bb.note("removing %s" % base_path_out(local, d))
			try:
				if os.path.exists(local + ".md5"):
					os.remove(local + ".md5")
				if os.path.exists(local):
					os.remove(local)
			except OSError, e:
				bb.note("Error in removal: %s" % e)
}

SCENEFUNCS += "base_scenefunction"
											
python base_do_setscene () {
        for f in (bb.data.getVar('SCENEFUNCS', d, 1) or '').split():
                bb.build.exec_func(f, d)
	if not os.path.exists(bb.data.getVar('STAMP', d, 1) + ".do_setscene"):
		bb.build.make_stamp("do_setscene", d)
}
do_setscene[selfstamp] = "1"
addtask setscene before do_fetch

python base_scenefunction () {
	stamp = bb.data.getVar('STAMP', d, 1) + ".needclean"
	if os.path.exists(stamp):
	        bb.build.exec_func("do_clean", d)
}


addtask fetch
do_fetch[dirs] = "${DL_DIR}"
do_fetch[depends] = "shasum-native:do_populate_staging"
python base_do_fetch() {
	import sys

	localdata = bb.data.createCopy(d)
	bb.data.update_data(localdata)

	src_uri = bb.data.getVar('SRC_URI', localdata, 1)
	if not src_uri:
		return 1

	try:
		bb.fetch.init(src_uri.split(),d)
	except bb.fetch.NoMethodError:
		(type, value, traceback) = sys.exc_info()
		raise bb.build.FuncFailed("No method: %s" % value)
	except bb.MalformedUrl:
		(type, value, traceback) = sys.exc_info()
		raise bb.build.FuncFailed("Malformed URL: %s" % value)

	try:
		bb.fetch.go(localdata)
	except bb.fetch.MissingParameterError:
		(type, value, traceback) = sys.exc_info()
		raise bb.build.FuncFailed("Missing parameters: %s" % value)
	except bb.fetch.FetchError:
		(type, value, traceback) = sys.exc_info()
		raise bb.build.FuncFailed("Fetch failed: %s" % value)
	except bb.fetch.MD5SumError:
		(type, value, traceback) = sys.exc_info()
		raise bb.build.FuncFailed("MD5  failed: %s" % value)
	except:
		(type, value, traceback) = sys.exc_info()
		raise bb.build.FuncFailed("Unknown fetch Error: %s" % value)


	# Verify the SHA and MD5 sums we have in OE and check what do
	# in
	checksum_paths = bb.data.getVar('BBPATH', d, True).split(":")

	# reverse the list to give precedence to directories that
	# appear first in BBPATH
	checksum_paths.reverse()

	checksum_files = ["%s/conf/checksums.ini" % path for path in checksum_paths]
	try:
		parser = base_chk_load_parser(checksum_files)
	except ValueError:
		bb.note("No conf/checksums.ini found, not checking checksums")
		return
	except:
		bb.note("Creating the CheckSum parser failed: %s:%s" % (sys.exc_info()[0], sys.exc_info()[1]))
		return

	pv = bb.data.getVar('PV', d, True)
	pn = bb.data.getVar('PN', d, True)

	# Check each URI
	for url in src_uri.split():
		localpath = bb.data.expand(bb.fetch.localpath(url, localdata), localdata)
		(type,host,path,_,_,params) = bb.decodeurl(url)
		uri = "%s://%s%s" % (type,host,path)
		try:
			if type in [ "http", "https", "ftp", "ftps" ]:
				if not (base_chk_file_vars(parser, localpath, params, d) or base_chk_file(parser, pn, pv,uri, localpath, d)):
					if not bb.data.getVar("OE_ALLOW_INSECURE_DOWNLOADS", d, True):
						bb.fatal("%s-%s: %s has no checksum defined, cannot check archive integrity" % (pn,pv,uri))
					else:
						bb.note("%s-%s: %s has no checksum defined, archive integrity not checked" % (pn,pv,uri))
		except Exception:
			raise bb.build.FuncFailed("Checksum of '%s' failed" % uri)
}

addtask fetchall after do_fetch
do_fetchall[recrdeptask] = "do_fetch"
base_do_fetchall() {
	:
}

addtask checkuri
do_checkuri[nostamp] = "1"
python do_checkuri() {
	import sys

	localdata = bb.data.createCopy(d)
	bb.data.update_data(localdata)

	src_uri = bb.data.getVar('SRC_URI', localdata, 1)

	try:
		bb.fetch.init(src_uri.split(),d)
	except bb.fetch.NoMethodError:
		(type, value, traceback) = sys.exc_info()
		raise bb.build.FuncFailed("No method: %s" % value)

	try:
		bb.fetch.checkstatus(localdata)
	except bb.fetch.MissingParameterError:
		(type, value, traceback) = sys.exc_info()
		raise bb.build.FuncFailed("Missing parameters: %s" % value)
	except bb.fetch.FetchError:
		(type, value, traceback) = sys.exc_info()
		raise bb.build.FuncFailed("Fetch failed: %s" % value)
	except bb.fetch.MD5SumError:
		(type, value, traceback) = sys.exc_info()
		raise bb.build.FuncFailed("MD5  failed: %s" % value)
	except:
		(type, value, traceback) = sys.exc_info()
		raise bb.build.FuncFailed("Unknown fetch Error: %s" % value)
}

addtask checkuriall after do_checkuri
do_checkuriall[recrdeptask] = "do_checkuri"
do_checkuriall[nostamp] = "1"
base_do_checkuriall() {
	:
}

addtask buildall after do_build
do_buildall[recrdeptask] = "do_build"
base_do_buildall() {
	:
}

def subprocess_setup():
	import signal
	# Python installs a SIGPIPE handler by default. This is usually not what
	# non-Python subprocesses expect.
	signal.signal(signal.SIGPIPE, signal.SIG_DFL)

def oe_unpack_file(file, data, url = None):
	import subprocess
	if not url:
		url = "file://%s" % file
	dots = file.split(".")
	if dots[-1] in ['gz', 'bz2', 'Z']:
		efile = os.path.join(bb.data.getVar('WORKDIR', data, 1),os.path.basename('.'.join(dots[0:-1])))
	else:
		efile = file
	cmd = None
	if file.endswith('.tar'):
		cmd = 'tar x --no-same-owner -f %s' % file
	elif file.endswith('.tgz') or file.endswith('.tar.gz') or file.endswith('.tar.Z'):
		cmd = 'tar xz --no-same-owner -f %s' % file
	elif file.endswith('.tbz') or file.endswith('.tbz2') or file.endswith('.tar.bz2'):
		cmd = 'bzip2 -dc %s | tar x --no-same-owner -f -' % file
	elif file.endswith('.gz') or file.endswith('.Z') or file.endswith('.z'):
		cmd = 'gzip -dc %s > %s' % (file, efile)
	elif file.endswith('.bz2'):
		cmd = 'bzip2 -dc %s > %s' % (file, efile)
	elif file.endswith('.tar.xz'):
		cmd = 'xz -dc %s | tar x --no-same-owner -f -' % file
	elif file.endswith('.xz'):
		cmd = 'xz -dc %s > %s' % (file, efile)
	elif file.endswith('.zip') or file.endswith('.jar'):
		cmd = 'unzip -q -o'
		(type, host, path, user, pswd, parm) = bb.decodeurl(url)
		if 'dos' in parm:
			cmd = '%s -a' % cmd
		cmd = '%s %s' % (cmd, file)
	elif os.path.isdir(file):
		destdir = "."
		filespath = bb.data.getVar("FILESPATH", data, 1).split(":")
		for fp in filespath:
			if file[0:len(fp)] == fp:
				destdir = file[len(fp):file.rfind('/')]
				destdir = destdir.strip('/')
				if len(destdir) < 1:
					destdir = "."
				elif not os.access("%s/%s" % (os.getcwd(), destdir), os.F_OK):
					os.makedirs("%s/%s" % (os.getcwd(), destdir))
				break

		cmd = 'cp -pPR %s %s/%s/' % (file, os.getcwd(), destdir)
	else:
		(type, host, path, user, pswd, parm) = bb.decodeurl(url)
		if not 'patch' in parm:
			# The "destdir" handling was specifically done for FILESPATH
			# items.  So, only do so for file:// entries.
			if type == "file":
				destdir = bb.decodeurl(url)[1] or "."
			else:
				destdir = "."
			bb.mkdirhier("%s/%s" % (os.getcwd(), destdir))
			cmd = 'cp %s %s/%s/' % (file, os.getcwd(), destdir)

	if not cmd:
		return True

	dest = os.path.join(os.getcwd(), os.path.basename(file))
	if os.path.exists(dest):
		if os.path.samefile(file, dest):
			return True

	# Change to subdir before executing command
	save_cwd = os.getcwd();
	parm = bb.decodeurl(url)[5]
	if 'subdir' in parm:
		newdir = ("%s/%s" % (os.getcwd(), parm['subdir']))
		bb.mkdirhier(newdir)
		os.chdir(newdir)

	cmd = "PATH=\"%s\" %s" % (bb.data.getVar('PATH', data, 1), cmd)
	bb.note("Unpacking %s to %s/" % (base_path_out(file, data), base_path_out(os.getcwd(), data)))
	ret = subprocess.call(cmd, preexec_fn=subprocess_setup, shell=True)

	os.chdir(save_cwd)

	return ret == 0

addtask unpack after do_fetch
do_unpack[dirs] = "${WORKDIR}"
python base_do_unpack() {
	import re

	localdata = bb.data.createCopy(d)
	bb.data.update_data(localdata)

	src_uri = bb.data.getVar('SRC_URI', localdata)
	if not src_uri:
		return
	src_uri = bb.data.expand(src_uri, localdata)
	for url in src_uri.split():
		try:
			local = bb.data.expand(bb.fetch.localpath(url, localdata), localdata)
		except bb.MalformedUrl, e:
			raise bb.build.FuncFailed('Unable to generate local path for malformed uri: %s' % e)
		if not local:
			raise bb.build.FuncFailed('Unable to locate local file for %s' % url)
		local = os.path.realpath(local)
		ret = oe_unpack_file(local, localdata, url)
		if not ret:
			raise bb.build.FuncFailed()
}

METADATA_SCM = "${@base_get_scm(d)}"
METADATA_REVISION = "${@base_get_scm_revision(d)}"
METADATA_BRANCH = "${@base_get_scm_branch(d)}"

def base_get_scm(d):
	from bb import which
	baserepo = os.path.dirname(os.path.dirname(which(d.getVar("BBPATH", 1), "classes/base.bbclass")))
	for (scm, scmpath) in {"svn": ".svn",
			       "git": ".git",
			       "monotone": "_MTN"}.iteritems():
		if os.path.exists(os.path.join(baserepo, scmpath)):
			return "%s %s" % (scm, baserepo)
	return "<unknown> %s" % baserepo

def base_get_scm_revision(d):
	(scm, path) = d.getVar("METADATA_SCM", 1).split()
	try:
		if scm != "<unknown>":
			return globals()["base_get_metadata_%s_revision" % scm](path, d)
		else:
			return scm
	except KeyError:
		return "<unknown>"

def base_get_scm_branch(d):
	(scm, path) = d.getVar("METADATA_SCM", 1).split()
	try:
		if scm != "<unknown>":
			return globals()["base_get_metadata_%s_branch" % scm](path, d)
		else:
			return scm
	except KeyError:
		return "<unknown>"

def base_get_metadata_monotone_branch(path, d):
	monotone_branch = "<unknown>"
	try:
		monotone_branch = file( "%s/_MTN/options" % path ).read().strip()
		if monotone_branch.startswith( "database" ):
			monotone_branch_words = monotone_branch.split()
			monotone_branch = monotone_branch_words[ monotone_branch_words.index( "branch" )+1][1:-1]
	except:
		pass
	return monotone_branch

def base_get_metadata_monotone_revision(path, d):
	monotone_revision = "<unknown>"
	try:
		monotone_revision = file( "%s/_MTN/revision" % path ).read().strip()
		if monotone_revision.startswith( "format_version" ):
			monotone_revision_words = monotone_revision.split()
			monotone_revision = monotone_revision_words[ monotone_revision_words.index( "old_revision" )+1][1:-1]
	except IOError:
		pass
	return monotone_revision

def base_get_metadata_svn_revision(path, d):
	revision = "<unknown>"
	try:
		revision = file( "%s/.svn/entries" % path ).readlines()[3].strip()
	except IOError:
		pass
	return revision

def base_get_metadata_git_branch(path, d):
	branch = os.popen('cd %s; PATH=%s git symbolic-ref HEAD 2>/dev/null' % (path, d.getVar("PATH", 1))).read().rstrip()

	if len(branch) != 0:
		return branch.replace("refs/heads/", "")
	return "<unknown>"

def base_get_metadata_git_revision(path, d):
	rev = os.popen("cd %s; PATH=%s git show-ref HEAD 2>/dev/null" % (path, d.getVar("PATH", 1))).read().split(" ")[0].rstrip()
	if len(rev) != 0:
		return rev
	return "<unknown>"


addhandler base_eventhandler
python base_eventhandler() {
	from bb import note, error, data
	from bb.event import Handled, NotHandled, getName


	name = getName(e)
	if name == "TaskCompleted":
		msg = "package %s: task %s is complete." % (data.getVar("PF", e.data, 1), e.task)
	elif name == "UnsatisfiedDep":
		msg = "package %s: dependency %s %s" % (e.pkg, e.dep, name[:-3].lower())
	else:
		return NotHandled

	# Only need to output when using 1.8 or lower, the UI code handles it
	# otherwise
	if (int(bb.__version__.split(".")[0]) <= 1 and int(bb.__version__.split(".")[1]) <= 8):
		if msg:
			note(msg)

	if name.startswith("BuildStarted"):
		bb.data.setVar( 'BB_VERSION', bb.__version__, e.data )
		statusvars = bb.data.getVar("BUILDCFG_VARS", e.data, 1).split()
		statuslines = ["%-17s = \"%s\"" % (i, bb.data.getVar(i, e.data, 1) or '') for i in statusvars]
		statusmsg = "\n%s\n%s\n" % (bb.data.getVar("BUILDCFG_HEADER", e.data, 1), "\n".join(statuslines))
		print statusmsg

		needed_vars = bb.data.getVar("BUILDCFG_NEEDEDVARS", e.data, 1).split()
		pesteruser = []
		for v in needed_vars:
			val = bb.data.getVar(v, e.data, 1)
			if not val or val == 'INVALID':
				pesteruser.append(v)
		if pesteruser:
			bb.fatal('The following variable(s) were not set: %s\nPlease set them directly, or choose a MACHINE or DISTRO that sets them.' % ', '.join(pesteruser))

	#
	# Handle removing stamps for 'rebuild' task
	#
	if name.startswith("StampUpdate"):
		for (fn, task) in e.targets:
			#print "%s %s" % (task, fn)         
			if task == "do_rebuild":
				dir = "%s.*" % e.stampPrefix[fn]
				bb.note("Removing stamps: " + dir)
				os.system('rm -f '+ dir)
				os.system('touch ' + e.stampPrefix[fn] + '.needclean')

	if not data in e.__dict__:
		return NotHandled

	log = data.getVar("EVENTLOG", e.data, 1)
	if log:
		logfile = file(log, "a")
		logfile.write("%s\n" % msg)
		logfile.close()

	return NotHandled
}

addtask configure after do_unpack do_patch
do_configure[dirs] = "${S} ${B}"
do_configure[deptask] = "do_populate_staging"
base_do_configure() {
	:
}

addtask compile after do_configure
do_compile[dirs] = "${S} ${B}"
base_do_compile() {
	if [ -e Makefile -o -e makefile ]; then
		oe_runmake || die "make failed"
	else
		oenote "nothing to compile"
	fi
}


sysroot_stage_dir() {
	src="$1"
	dest="$2"
	# This will remove empty directories so we can ignore them
	rmdir "$src" 2> /dev/null || true
	if [ -d "$src" ]; then
		mkdir -p "$dest"
		cp -fpPR "$src"/* "$dest"
	fi
}

sysroot_stage_libdir() {
	src="$1"
	dest="$2"

	olddir=`pwd`
	cd $src
	las=$(find . -name \*.la -type f)
	cd $olddir
	echo "Found la files: $las"		 
	for i in $las
	do
		sed -e 's/^installed=yes$/installed=no/' \
		    -e '/^dependency_libs=/s,${WORKDIR}[[:alnum:]/\._+-]*/\([[:alnum:]\._+-]*\),${STAGING_LIBDIR}/\1,g' \
		    -e "/^dependency_libs=/s,\([[:space:]']\)${libdir},\1${STAGING_LIBDIR},g" \
		    -i $src/$i
	done
	sysroot_stage_dir $src $dest
}

sysroot_stage_dirs() {
	from="$1"
	to="$2"

	sysroot_stage_dir $from${includedir} $to${STAGING_INCDIR}
	if [ "${BUILD_SYS}" = "${HOST_SYS}" ]; then
		sysroot_stage_dir $from${bindir} $to${STAGING_DIR_HOST}${bindir}
		sysroot_stage_dir $from${sbindir} $to${STAGING_DIR_HOST}${sbindir}
		sysroot_stage_dir $from${base_bindir} $to${STAGING_DIR_HOST}${base_bindir}
		sysroot_stage_dir $from${base_sbindir} $to${STAGING_DIR_HOST}${base_sbindir}
		sysroot_stage_dir $from${libexecdir} $to${STAGING_DIR_HOST}${libexecdir}
		if [ "${prefix}/lib" != "${libdir}" ]; then
			# python puts its files in here, make sure they are staged as well
			autotools_stage_dir $from/${prefix}/lib $to${STAGING_DIR_HOST}${prefix}/lib
		fi
	fi
	if [ -d $from${libdir} ]
	then
		sysroot_stage_libdir $from/${libdir} $to${STAGING_LIBDIR}
	fi
	if [ -d $from${base_libdir} ]
	then
		sysroot_stage_libdir $from${base_libdir} $to${STAGING_DIR_HOST}${base_libdir}
	fi
	sysroot_stage_dir $from${datadir} $to${STAGING_DATADIR}
}

sysroot_stage_all() {
	sysroot_stage_dirs ${D} ${SYSROOT_DESTDIR}
}

def is_legacy_staging(d):
    stagefunc = bb.data.getVar('do_stage', d, True)
    legacy = True
    if stagefunc is None:
        legacy = False
    elif stagefunc.strip() == "autotools_stage_all":
        legacy = False
    elif stagefunc.strip() == "do_stage_native" and bb.data.getVar('AUTOTOOLS_NATIVE_STAGE_INSTALL', d, 1) == "1":
        legacy = False
    elif bb.data.getVar('NATIVE_INSTALL_WORKS', d, 1) == "1":
        legacy = False
    if bb.data.getVar('PSTAGE_BROKEN_DESTDIR', d, 1) == "1":
        legacy = True
    if bb.data.getVar('FORCE_LEGACY_STAGING', d, 1) == "1":
        legacy = True
    return legacy

do_populate_staging[dirs] = "${STAGING_DIR_TARGET}/${bindir} ${STAGING_DIR_TARGET}/${libdir} \
			     ${STAGING_DIR_TARGET}/${includedir} \
			     ${STAGING_BINDIR_NATIVE} ${STAGING_LIBDIR_NATIVE} \
			     ${STAGING_INCDIR_NATIVE} \
			     ${STAGING_DATADIR} \
			     ${S} ${B}"

# Could be compile but populate_staging and do_install shouldn't run at the same time
addtask populate_staging after do_install

SYSROOT_PREPROCESS_FUNCS ?= ""
SYSROOT_DESTDIR = "${WORKDIR}/sysroot-destdir/"
SYSROOT_LOCK = "${STAGING_DIR}/staging.lock"

python populate_staging_prehook () {
	return
}

python populate_staging_posthook () {
	return
}

packagedstaging_fastpath () {
	:
}

python do_populate_staging () {
    #
    # if do_stage exists, we're legacy. In that case run the do_stage,
    # modify the SYSROOT_DESTDIR variable and then run the staging preprocess
    # functions against staging directly.
    #
    # Otherwise setup a destdir, copy the results from do_install
    # and run the staging preprocess against that
    #
    pstageactive = (bb.data.getVar("PSTAGING_ACTIVE", d, True) == "1")
    lockfile = bb.data.getVar("SYSROOT_LOCK", d, True)
    stagefunc = bb.data.getVar('do_stage', d, True)
    legacy = is_legacy_staging(d)
    if legacy:
        bb.data.setVar("SYSROOT_DESTDIR", "", d)
        bb.note("Legacy staging mode for %s" % bb.data.getVar("FILE", d, True))

        try:
            file = open("%s/legacy-staging.log" % bb.data.getVar("TMPDIR", d, 1), "a")
	    file.write("%s\n" % bb.data.getVar("FILE", d, True))
	    file.close()
        except:
            pass

        if bb.data.getVarFlags('do_stage', d) is None:
            bb.fatal("This recipe (%s) has a do_stage_prepend or do_stage_append and do_stage now doesn't exist. Please rename this to do_stage()" % bb.data.getVar("FILE", d, True))
        lock = bb.utils.lockfile(lockfile)
        bb.build.exec_func('populate_staging_prehook', d)
        bb.build.exec_func('do_stage', d)
        for f in (bb.data.getVar('SYSROOT_PREPROCESS_FUNCS', d, True) or '').split():
            bb.build.exec_func(f, d)
        bb.build.exec_func('populate_staging_posthook', d)
        bb.utils.unlockfile(lock)
    else:
        dest = bb.data.getVar('D', d, True)
        sysrootdest = bb.data.expand('${SYSROOT_DESTDIR}${STAGING_DIR_TARGET}', d)
        bb.mkdirhier(sysrootdest)

        bb.build.exec_func("sysroot_stage_all", d)
        #os.system('cp -pPR %s/* %s/' % (dest, sysrootdest))
        for f in (bb.data.getVar('SYSROOT_PREPROCESS_FUNCS', d, True) or '').split():
            bb.build.exec_func(f, d)
        bb.build.exec_func("packagedstaging_fastpath", d)

        lock = bb.utils.lockfile(lockfile)
        os.system(bb.data.expand('cp -pPR ${SYSROOT_DESTDIR}${TMPDIR}/* ${TMPDIR}/', d))
        bb.utils.unlockfile(lock)
}

addtask install after do_compile
do_install[dirs] = "${D} ${S} ${B}"
# Remove and re-create ${D} so that is it guaranteed to be empty
do_install[cleandirs] = "${D}"

base_do_install() {
	:
}

base_do_package() {
	:
}

addtask build after do_populate_staging
do_build = ""
do_build[func] = "1"

inherit packagedata

# Functions that update metadata based on files outputted
# during the build process.

def explode_deps(s):
	r = []
	l = s.split()
	flag = False
	for i in l:
		if i[0] == '(':
			flag = True
			j = []
		if flag:
			j.append(i)
			if i.endswith(')'):
				flag = False
				r[-1] += ' ' + ' '.join(j)
		else:
			r.append(i)
	return r

# Make sure MACHINE isn't exported
# (breaks binutils at least)
MACHINE[unexport] = "1"

# Make sure TARGET_ARCH isn't exported
# (breaks Makefiles using implicit rules, e.g. quilt, as GNU make has this 
# in them, undocumented)
TARGET_ARCH[unexport] = "1"

# Make sure DISTRO isn't exported
# (breaks sysvinit at least)
DISTRO[unexport] = "1"


def base_after_parse(d):
    import exceptions

    source_mirror_fetch = bb.data.getVar('SOURCE_MIRROR_FETCH', d, 0)
    if not source_mirror_fetch:
        need_host = bb.data.getVar('COMPATIBLE_HOST', d, 1)
        if need_host:
            import re
            this_host = bb.data.getVar('HOST_SYS', d, 1)
            if not re.match(need_host, this_host):
                raise bb.parse.SkipPackage("incompatible with host %s" % this_host)

        need_machine = bb.data.getVar('COMPATIBLE_MACHINE', d, 1)
        if need_machine:
            import re
            this_machine = bb.data.getVar('MACHINE', d, 1)
            if this_machine and not re.match(need_machine, this_machine):
                raise bb.parse.SkipPackage("incompatible with machine %s" % this_machine)

    pn = bb.data.getVar('PN', d, 1)

    # OBSOLETE in bitbake 1.7.4
    srcdate = bb.data.getVar('SRCDATE_%s' % pn, d, 1)
    if srcdate != None:
        bb.data.setVar('SRCDATE', srcdate, d)

    use_nls = bb.data.getVar('USE_NLS_%s' % pn, d, 1)
    if use_nls != None:
        bb.data.setVar('USE_NLS', use_nls, d)

    # Git packages should DEPEND on git-native
    srcuri = bb.data.getVar('SRC_URI', d, 1)
    if "git://" in srcuri:
        depends = bb.data.getVarFlag('do_fetch', 'depends', d) or ""
        depends = depends + " git-native:do_populate_staging"
        bb.data.setVarFlag('do_fetch', 'depends', depends, d)

    # 'multimachine' handling
    mach_arch = bb.data.getVar('MACHINE_ARCH', d, 1)
    pkg_arch = bb.data.getVar('PACKAGE_ARCH', d, 1)

    if (pkg_arch == mach_arch):
        # Already machine specific - nothing further to do
        return

    #
    # We always try to scan SRC_URI for urls with machine overrides
    # unless the package sets SRC_URI_OVERRIDES_PACKAGE_ARCH=0
    #
    override = bb.data.getVar('SRC_URI_OVERRIDES_PACKAGE_ARCH', d, 1)
    if override != '0':
        paths = []
        for p in [ "${PF}", "${P}", "${PN}", "files", "" ]:
            path = bb.data.expand(os.path.join("${FILE_DIRNAME}", p, "${MACHINE}"), d)
            if os.path.isdir(path):
                paths.append(path)
        if len(paths) != 0:
            for s in srcuri.split():
                if not s.startswith("file://"):
                    continue
                local = bb.data.expand(bb.fetch.localpath(s, d), d)
                for mp in paths:
                    if local.startswith(mp):
                        #bb.note("overriding PACKAGE_ARCH from %s to %s" % (pkg_arch, mach_arch))
                        bb.data.setVar('PACKAGE_ARCH', "${MACHINE_ARCH}", d)
                        bb.data.setVar('MULTIMACH_ARCH', mach_arch, d)
                        return

    multiarch = pkg_arch

    packages = bb.data.getVar('PACKAGES', d, 1).split()
    for pkg in packages:
        pkgarch = bb.data.getVar("PACKAGE_ARCH_%s" % pkg, d, 1)

        # We could look for != PACKAGE_ARCH here but how to choose 
        # if multiple differences are present?
        # Look through PACKAGE_ARCHS for the priority order?
        if pkgarch and pkgarch == mach_arch:
            multiarch = mach_arch
            break

    bb.data.setVar('MULTIMACH_ARCH', multiarch, d)

python () {
    base_after_parse(d)
    if is_legacy_staging(d):
        bb.debug(1, "Legacy staging mode for %s" % bb.data.getVar("FILE", d, True))
        if bb.data.getVarFlags('do_stage', d) is None:
            bb.error("This recipe (%s) has a do_stage_prepend or do_stage_append and do_stage now doesn't exist. Please rename this to do_stage()" % bb.data.getVar("FILE", d, True))

}

def check_app_exists(app, d):
	from bb import which, data

	app = data.expand(app, d)
	path = data.getVar('PATH', d, 1)
	return len(which(path, app)) != 0

# Patch handling
inherit patch

# Configuration data from site files
# Move to autotools.bbclass?
inherit siteinfo

EXPORT_FUNCTIONS do_setscene do_clean do_mrproper do_distclean do_fetch do_unpack do_configure do_compile do_install do_package do_populate_pkgs do_rebuild do_fetchall

MIRRORS[func] = "0"
MIRRORS () {
${DEBIAN_MIRROR}/main	http://snapshot.debian.net/archive/pool
${DEBIAN_MIRROR}	ftp://ftp.de.debian.org/debian/pool
${DEBIAN_MIRROR}	ftp://ftp.au.debian.org/debian/pool
${DEBIAN_MIRROR}	ftp://ftp.cl.debian.org/debian/pool
${DEBIAN_MIRROR}	ftp://ftp.hr.debian.org/debian/pool
${DEBIAN_MIRROR}	ftp://ftp.fi.debian.org/debian/pool
${DEBIAN_MIRROR}	ftp://ftp.hk.debian.org/debian/pool
${DEBIAN_MIRROR}	ftp://ftp.hu.debian.org/debian/pool
${DEBIAN_MIRROR}	ftp://ftp.ie.debian.org/debian/pool
${DEBIAN_MIRROR}	ftp://ftp.it.debian.org/debian/pool
${DEBIAN_MIRROR}	ftp://ftp.jp.debian.org/debian/pool
${DEBIAN_MIRROR}	ftp://ftp.no.debian.org/debian/pool
${DEBIAN_MIRROR}	ftp://ftp.pl.debian.org/debian/pool
${DEBIAN_MIRROR}	ftp://ftp.ro.debian.org/debian/pool
${DEBIAN_MIRROR}	ftp://ftp.si.debian.org/debian/pool
${DEBIAN_MIRROR}	ftp://ftp.es.debian.org/debian/pool
${DEBIAN_MIRROR}	ftp://ftp.se.debian.org/debian/pool
${DEBIAN_MIRROR}	ftp://ftp.tr.debian.org/debian/pool
${GNU_MIRROR}	ftp://mirrors.kernel.org/gnu
${GNU_MIRROR}	ftp://ftp.cs.ubc.ca/mirror2/gnu
${GNU_MIRROR}	ftp://sunsite.ust.hk/pub/gnu
${GNU_MIRROR}	ftp://ftp.ayamura.org/pub/gnu
${KERNELORG_MIRROR}	http://www.kernel.org/pub
${KERNELORG_MIRROR}	ftp://ftp.us.kernel.org/pub
${KERNELORG_MIRROR}	ftp://ftp.uk.kernel.org/pub
${KERNELORG_MIRROR}	ftp://ftp.hk.kernel.org/pub
${KERNELORG_MIRROR}	ftp://ftp.au.kernel.org/pub
${KERNELORG_MIRROR}	ftp://ftp.jp.kernel.org/pub
ftp://ftp.gnupg.org/gcrypt/     ftp://ftp.franken.de/pub/crypt/mirror/ftp.gnupg.org/gcrypt/
ftp://ftp.gnupg.org/gcrypt/     ftp://ftp.surfnet.nl/pub/security/gnupg/
ftp://ftp.gnupg.org/gcrypt/     http://gulus.USherbrooke.ca/pub/appl/GnuPG/
ftp://dante.ctan.org/tex-archive ftp://ftp.fu-berlin.de/tex/CTAN
ftp://dante.ctan.org/tex-archive http://sunsite.sut.ac.jp/pub/archives/ctan/
ftp://dante.ctan.org/tex-archive http://ctan.unsw.edu.au/
ftp://ftp.gnutls.org/pub/gnutls ftp://ftp.gnutls.org/pub/gnutls/
ftp://ftp.gnutls.org/pub/gnutls ftp://ftp.gnupg.org/gcrypt/gnutls/
ftp://ftp.gnutls.org/pub/gnutls http://www.mirrors.wiretapped.net/security/network-security/gnutls/
ftp://ftp.gnutls.org/pub/gnutls ftp://ftp.mirrors.wiretapped.net/pub/security/network-security/gnutls/
ftp://ftp.gnutls.org/pub/gnutls http://josefsson.org/gnutls/releases/
http://ftp.info-zip.org/pub/infozip/src/ http://mirror.switch.ch/ftp/mirror/infozip/src/
http://ftp.info-zip.org/pub/infozip/src/ ftp://sunsite.icm.edu.pl/pub/unix/archiving/info-zip/src/
ftp://lsof.itap.purdue.edu/pub/tools/unix/lsof/  ftp://ftp.cerias.purdue.edu/pub/tools/unix/sysutils/lsof/
ftp://lsof.itap.purdue.edu/pub/tools/unix/lsof/  ftp://ftp.tau.ac.il/pub/unix/admin/
ftp://lsof.itap.purdue.edu/pub/tools/unix/lsof/  ftp://ftp.cert.dfn.de/pub/tools/admin/lsof/
ftp://lsof.itap.purdue.edu/pub/tools/unix/lsof/  ftp://ftp.fu-berlin.de/pub/unix/tools/lsof/
ftp://lsof.itap.purdue.edu/pub/tools/unix/lsof/  ftp://ftp.kaizo.org/pub/lsof/
ftp://lsof.itap.purdue.edu/pub/tools/unix/lsof/  ftp://ftp.tu-darmstadt.de/pub/sysadmin/lsof/
ftp://lsof.itap.purdue.edu/pub/tools/unix/lsof/  ftp://ftp.tux.org/pub/sites/vic.cc.purdue.edu/tools/unix/lsof/
ftp://lsof.itap.purdue.edu/pub/tools/unix/lsof/  ftp://gd.tuwien.ac.at/utils/admin-tools/lsof/
ftp://lsof.itap.purdue.edu/pub/tools/unix/lsof/  ftp://sunsite.ualberta.ca/pub/Mirror/lsof/
ftp://lsof.itap.purdue.edu/pub/tools/unix/lsof/  ftp://the.wiretapped.net/pub/security/host-security/lsof/
http://www.apache.org/dist  http://archive.apache.org/dist
ftp://.*/.*     http://mirrors.openembedded.org/
https?$://.*/.* http://mirrors.openembedded.org/
ftp://.*/.*     http://sources.openembedded.org/
https?$://.*/.* http://sources.openembedded.org/

}

