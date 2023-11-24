class Optie extends Model
{
    protected $table = 'opties';

    protected $fillable = [
        'S_id',
        'optie',
    ];

    public function stelling() {
        return $this->belongsTo(Stelling::class, 'S_id');
    }
}



class Stelling extends Model
{
    use HasFactory;

    protected $table = 'stelling';

    protected $fillable = [
        'title',
        'beschrijving',
    ];

    public function opties(){
        return $this->hasMany(Optie::class, 'S_id');
    }
}


return new class extends Migration
{
    /**
     * Run the migrations.
     */
    public function up(): void
    {
        Schema::create('stelling', function (Blueprint $table) {
            $table->id();
            $table->string('title');
            $table->text('beschrijving');
            $table->timestamps();
        });
    }

    /**
     * Reverse the migrations.
     */
    public function down(): void
    {
        Schema::dropIfExists('stelling');
    }
};


return new class extends Migration
{
    /**
     * Run the migrations.
     */
    public function up(): void
    {
        Schema::create('opties', function (Blueprint $table) {
            $table->id();
            $table->unsignedBigInteger('S_id');
            $table->foreign('S_id')->references('id')->on('stelling')->onDelete('cascade');
            $table->string('optie');
            $table->timestamps();
        });
    }

    /**
     * Reverse the migrations.
     */
    public function down(): void
    {
        Schema::dropIfExists('opties');
    }
};
