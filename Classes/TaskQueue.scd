


a = TaskQueue.new;

a.add({ "hello 0".postln; 2.wait; "hello 1".postln; 1.wait });
a.add({ "hello 2".postln; 2.wait; "hello 3".postln; 4.wait });

a.queue;

a.play;

a.add({ "hello 4".postln; 2.wait; "hello 5".postln; 1.wait });
a.add({ "hello 6".postln; 2.wait; "hello 7".postln; 4.wait });


a.stop;


// same simpler



(
var queue = LinkedList.new;
var routine;
f = { |func|
	queue.add(func);
	if(routine.isPlaying.not) { routine.reset.play };
};
routine = Routine {
	var func;
	while {
		func = queue.popFirst;
		func.notNil
	} {
		func.value
	}
}
)
